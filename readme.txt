1. Install NGINX ingress
helm -n ingress install ingress ./ingress-nginx

2. Install istio
helm repo add istio https://istio-release.storage.googleapis.com/charts
helm repo update

kubectl create namespace istio-system

helm install istio-base istio/base -n istio-system --set defaultRevision=default

helm install istiod istio/istiod -n istio-system --wait

kubectl create namespace istio-ingress
helm install istio-ingress istio/gateway -n istio-ingress --wait

3. Build Image for frontend
docker build -f Dockerfile -t siddhivinayaksk/digistore-frontendms:0.0.1 .
docker push siddhivinayaksk/digistore-frontendms:0.0.1

kubectl apply -f digistore-frontend-ms-k8-deployment.yaml

4. Build Image for backend
mvn clean package
docker build --build-arg VER=0.0.1 -f dockerfile -t siddhivinayaksk/digistore-productms:0.0.1 .
docker push siddhivinayaksk/digistore-productms:0.0.1
docker build --build-arg VER=0.0.2 -f dockerfile -t siddhivinayaksk/digistore-productms:0.0.2 .
docker push siddhivinayaksk/digistore-productms:0.0.2

kubectl apply -f digistore-product-ms-k8-deployment_0.0.1.yaml
kubectl apply -f digistore-product-ms-k8-deployment_0.0.2.yaml

5. Create tls secret
kubectl create -n istio-ingress secret tls istio-ingress-crt --key=client-private-key.pem --cert=client-certificate.crt

6. Create istio components
kubectl apply -f istio-gateway.yml
