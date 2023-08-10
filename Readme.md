#Echo Server


## Description
This is a spring boot application that has a /echo endpoint that will return whatever json that is it receives. 

## Pre-requisites 
1.Docker 
2. Kubectl
3. Minikube

Run the following commands to provides instructions to point your terminal’s docker-cli to the Docker Engine inside minikube. (Useful for building docker images directly inside minikube)

```
1. minikube docker-env
2. eval $(minikube -p minikube docker-env)
```


## Deployment steps

### Method 1 
2. docker build . -t echoserver
3. docker run -d -p 8080:8080 -t echoserver --rm -it echoserver1
3. docker run -d -p 8081:8080 -t echoserver --rm -it echoserver2
3. docker run -d -p 8082:8080 -t echoserver --rm -it echoserver3

### Method 2
To run with kubernetes with 3 replicas: 
1. minikube start --driver=docker 
2. docker build . -t echoserver
3.  minikube docker-env
4. eval $(minikube -p minikube docker-env)
5. minikube image load echoserver
6. minikube tunnel
7. kubectl apply -f deployment.yaml
8. kubectl get deployments
9. kubectl get pods
10. kubectl expose deployment echo server --port=8080  --name=echoserver-service --type=LoadBalancer
11. minikube service echoserver --url
**todo: find a way to get ip and port of each pod in the cluster for routing api
    
## Path
http://localhost:<port>/swagger-ui/index.html
http://localhost:<port>/actuator/health