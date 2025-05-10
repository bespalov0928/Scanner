# Scanner
## Docker
#### графана 
docker run --name scanner-grafana -p 3000:3000 -v /mnt/c/projects/Scanner/data/grafana:/home/ubuntu/grafana grafana/grafana:10.2.4
#### Метрики 
docker run --name scanner-metrics -p 8428:8428 -v /mnt/c/projects/Scanner/config/victoria-metrics/promscrape.yaml:/promscrape.yaml victoriametrics/victoria-metrics:v1.93.12 -promscrape.config=promscrape.yaml
#### логи 
docker run --name scanner-loki -p 3100:3100 grafana/loki:2.9.4
#### трассировка 
docker run --name scanner-tracing -p 3200:3200 -p 9095:9095 -p 4317:4317 -p 4318:4318 -p 9411:9411 -p 14268:14268 -v /mnt/c/projects/Scanner/config/tempo/tempo.yaml:/etc/tempo.yaml grafana/tempo:2.3.1 -config.file=/etc/tempo.yaml

## биржи для арбитража
#### Bybit https://partner.bybit.com/b/repin реф. код repin
#### Bingx https://bingx.com/invite/IJOKMY реф. код IJOKMY
#### Bitget https://partner.bitget.com/bg/544DZ9
#### Kucoin https://www.kucoin.com/r/af/r3CFCZP?u...
#### Huobi https://bit.ly/3L0LJPS
#### Mexc https://bit.ly/repinmexc реф. код: 1T7t3
#### CoinW https://bit.ly/repincoinw реф. код: 2198925
#### Gate https://bit.ly/3tZaxAV