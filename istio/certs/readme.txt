URL: https://www.acg-digistore.com/

#Step 1: Generate Public Certificate and Private Key
openssl req -x509 -nodes -days 730 -newkey rsa:4096 -keyout client-private-key.pem -out client-certificate.crt -config req.conf -extensions 'v3_req'

Passphrase: prashant

#Step 2: Crete PKCS with generated certificate and private key 
openssl pkcs12 -export -out client.p12.pfx -inkey client-private-key.pem -in client-certificate.crt

kubectl create -n istio-ingress secret tls istio-ingress-crt --key=client-private-key.pem --cert=client-certificate.crt




Certificate with start and end date:
1. openssl genpkey -algorithm RSA -out private_key.pem -aes256
2. openssl req -new -key private_key.pem -out csr.pem
3. Create a file openssl.conf
[ ca ]
default_ca = CA_default

[ CA_default ]
default_days = 365
default_md = sha256
policy = policy_anything
x509_extensions = v3_ca
database = index.txt
serial = serial.txt
new_certs_dir = ./newcerts

[ policy_anything ]
countryName = optional
stateOrProvinceName = optional
organizationName = optional
organizationalUnitName = optional
commonName = optional
emailAddress = optional

[ v3_ca ]
subjectKeyIdentifier = hash
authorityKeyIdentifier = keyid:always,issuer
basicConstraints = CA:true
4. touch index.txt
5. echo 1000 > serial.txt
6. mkdir newcerts
7. openssl ca -selfsign -keyfile private_key.pem -in csr.pem -out certificate.crt -startdate 20300101000000Z -enddate 20400101000000Z -config openssl.cnf

