ip=$(dig +short myip.opendns.com @resolver1.opendns.com)
echo $ip
curl -XPOST -d "{\"hostname\":\"$1\",\"ip\":\"$ip\"}" -H "Content-Type:application/json" http://tim-dns.herokuapp.com/dns
