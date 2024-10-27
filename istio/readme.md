#### Istio Components:
1. IstioOperator
2. Gateway
3. VirtualService
4. DestinationRule
5. ServiceEntry
6. EnvoyFilter

a. istio installed in istio-system namespace including Jaeger, Kiyali, Zipkin \
b. istio-ingress namespace created for ingress traffic (gateway, destinationrule, serviceentry) \
c. by default, all outbound traffic are allowed (`outboundTrafficPolicy.mode: ALLOW_ANY`) in istio but it can be blocked and egress gateway can be configured to control the outbound traffic. To do this, edit istio-system (namespace wehre istio installed) -> istio configMap and add below: \
```
    outboundTrafficPolicy:
      mode: REGISTRY_ONLY
```
d. 2 ways for egress \
  c1. istio-egress namespace created for egress traffic (gateway, destinationrule, serviceentry) if single central egress gateway to be used \
  c2. in each use case namespace egress-gateway (gateway, virtualservice [3 entries for each external url], destinationrule [one entry for each external url], serviceentry [one entry for each external url]) \



##### Install NGINX ingress (optional: if wanted to K8s ingress gateway)

```
helm -n ingress install ingress ./ingress-nginx
```

##### Install istio - There are multiple ways to install istio. 

Most popular are 
 - using `istioctl` cmd
 - using helm bundles

Ref.: https://istio.io/latest/docs/setup/getting-started/

A. Using `istioctl` \
A1. Download istio binary from https://github.com/istio/istio/releases/tag/1.19.0 \
A2. istioctl install --set profile=demo -y \
istioctl x create-remote-secret --kubeconfig /C/Users/sankumar/.kube/config --name=docker-desktop \
A3. kubectl label namespace default istio-injection=enabled \

Test
A4. kubectl apply -f samples/bookinfo/platform/kube/bookinfo.yaml \
A5. kubectl get services \
A6. kubectl get pods \
A7. kubectl exec "$(kubectl get pod -l app=ratings -o jsonpath='{.items[0].metadata.name}')" -c ratings -- curl -sS productpage:9080/productpage | grep -o "<title>.*</title>" \


A8. kubectl apply -f samples/bookinfo/networking/bookinfo-gateway.yaml \
A9. istioctl analyze \
A10. kubectl get svc istio-ingressgateway -n istio-system \
A11.  \
export INGRESS_HOST=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].ip}') \
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].port}') \
export SECURE_INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="https")].port}') \
export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT \
echo "$GATEWAY_URL" \


B. Using helm bundle

Add repo to helm repo
```
helm repo add istio https://istio-release.storage.googleapis.com/charts
```

helm repo update
```
helm repo update
```

create istio-system namspace
```
kubectl create namespace istio-system
```

install istio-base in istio-system namespace
```
helm install istio-base istio/base -n istio-system --set defaultRevision=default
```

install istiod in istio-system namespace
```
helm install istiod istio/istiod -n istio-system --wait
```

create namespace for installing istio/ingress-gateway
```
kubectl create namespace istio-ingress
```

install istio/ingress-gateway
```
helm install istio-ingress istio/gateway -n istio-ingress --wait
```

mark default namespace to enable isto proxy injector by labelling it

```
kubectl label namespace default istio-injection=enabled
```



#### Ingress Traffic Control using Istio

1. Build Image for frontend

build docker image and push to docker (optional: as already images are available in docker hub public repository)
```
docker build -f Dockerfile -t siddhivinayaksk/digistore-frontendms:0.0.1 .
docker push siddhivinayaksk/digistore-frontendms:0.0.1
```

install front end app (backed by node project)
```
kubectl apply -f digistore-frontend-ms-k8-deployment.yaml
```

2. Build Image for backend

build docker image and push to docker (optional: as already images are available in docker hub public repository)
```
mvn clean package
docker build --build-arg VER=0.0.1 -f dockerfile -t siddhivinayaksk/digistore-productms:0.0.1 .
docker push siddhivinayaksk/digistore-productms:0.0.1
docker build --build-arg VER=0.0.2 -f dockerfile -t siddhivinayaksk/digistore-productms:0.0.2 .
docker push siddhivinayaksk/digistore-productms:0.0.2
```

install two instanaces of backend app (backed by Java & Spring boot based application)
```
kubectl apply -f digistore-product-ms-k8-deployment_0.0.1.yaml
kubectl apply -f digistore-product-ms-k8-deployment_0.0.2.yaml
```

3. Create tls secret
```
kubectl create -n istio-ingress secret tls istio-ingress-crt --key=client-private-key.pem --cert=client-certificate.crt
```

4. Create istio components
```
kubectl apply -f istio-ingress.yml
```

5. Add DNS entries for istio ingress service to domain name defiend for aplication
if running on local the /etc/host can be used to add the DNS entries locally
```
127.0.0.0 acg-digistore.com www.acg-digistore.com
```

6. Open acg-digistore.com on browser for testing


#### Egress traffic

There are two ways to implement Egress:

##### A. Direct to external service

1. Create egress service entry

```
kubectl apply -f istio-egress.yml
```

2. Create a test container for curl

```
kubectl run -it busybox --image=curlimages/curl:latest -- /bin/sh
```

3. Test egress traffic

```
curl http://httpbin.org
```


##### Via Gateway (provides more granular control and telemetry

1. Install egress gateway \

Note: both ingress and egress gateways are same, only difference is, there will not be external ip for egress so service type is set to ClusterIP

create namespace for installing istio/egress-gateway

```
kubectl create namespace istio-egress

kubectl label namespace istio-egress istio=egress //Optional: mark this namespace as istio system only required when K8s network policy configured
kubectl label namespace istio-egress istio-egress=true //Optional: mark this namespace as istio system when K8s network policy configured
```

Install the egress gateway
```
helm install istio-egress istio/gateway -n istio-egress --set service.type=ClusterIP --wait
```

2. Create istio egress components

```
kubectl apply -f istio-egress-via-gateway.yml
```
Note: It has been configured for 2 URLs (httpbin.org and google.com)

3. Create a test container for curl

```
kubectl run -it busybox --image=curlimages/curl:latest -- /bin/sh
```

4. Test egress traffic

```
curl http://httpbin-org-svc/get
curl http://google-com
```


#### Telemetry

By default logs and telemetry are not available but it can be installed if requried.

To enable traffic log on `istio-proxy` create telemetry resource:

```
kubectl apply -f istio-telemetry.yml
```

For complete telemetry and other tools, sample installation can be used e.g.:

```
kubectl apply -f istio-samples/addons
```


