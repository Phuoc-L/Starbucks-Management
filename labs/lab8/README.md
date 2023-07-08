# CMPE 172 - Lab #8 Notes

## Kong API Gateway on Docker

    Kong API Gateway running on Docker. Ping test with and without the api key.

![docker](./images/docker.png)

## Kong API Gateway on GKE

    To run the app on GKE, i first pushed the image of the app onto my docker hub. Then created a Kubernetes cluster and deploy the image from my docker hub.

![deployment](./images/deployment.png)

    Deploy the load balancer for the app

![service](./images/service.png)

    Test pining the api in jumpbox with curl

![pingapi](./images/pingapi.png)

    Deploy kong api gate way in the cluster, connected to the load balancer

![deploykong](./images/deploykong.png)

    Test pinging kong api gateway

![pingkong](./images/pingkong.png)

    Test pinging kong ingress

![ingresskongping](./images/ingresskongping.png)

    Create key for the kong api gateway

![kongkey](./images/kongkey.png)

    Create kong secrete

![kongsecrete](./images/kongsecrete.png)

    Pining the starbucks api through the kong pai gateway with the api key

![apikeyping](./images/apikeyping.png)
