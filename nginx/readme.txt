kubectl create namespace kube-ingress

helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

helm -n kube-ingress install ingress-classic ingress-nginx --set controller.service.ports.http=9080 --set controller.service.ports.https=9443

kubectl -n kube-ingress apply -f httpbin-deployment.yaml







