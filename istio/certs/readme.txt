URL: https://www.acg-digistore.com/

#Step 1: Generate Public Certificate and Private Key
openssl req -x509 -nodes -days 730 -newkey rsa:4096 -keyout client-private-key.pem -out client-certificate.crt -config req.conf -extensions 'v3_req'

Passphrase: prashant

#Step 2: Crete PKCS with generated certificate and private key 
openssl pkcs12 -export -out client.p12.pfx -inkey client-private-key.pem -in client-certificate.crt

kubectl create -n istio-ingress secret tls istio-ingress-crt --key=client-private-key.pem --cert=client-certificate.crt
