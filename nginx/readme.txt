kubectl create namespace kube-ingress

There are several implementor of ingress. NGINX is most popular.

Install:
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

helm -n kube-ingress install ingress-classic ingress-nginx --set controller.service.ports.http=9080 --set controller.service.ports.https=9443


Add workload:
kubectl -n default apply -f httpbin-deployment.yaml
