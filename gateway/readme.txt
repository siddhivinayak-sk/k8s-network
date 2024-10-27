Readme:
https://kubernetes.io/docs/concepts/services-networking/gateway/
https://gateway-api.sigs.k8s.io/guides/

There are several implementors for the Gateway API:
 - Istio
 - Kong
 - NGrok


kubectl create namespace kube-gateway



kubectl -n kube-gateway apply -f https://github.com/kubernetes-sigs/gateway-api/releases/download/v1.2.0/standard-install.yaml

