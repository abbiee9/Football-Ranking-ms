Sample Football.json:
		
				{
				    "countryId": "c0bb338e-ef17-4bad-9d33-13f8ec4e7335",
				    "countryName": "India",
				    "leagueName": "ISL",
				    "leagueId": "9698e819-fada-4443-b783-7349de066145",
				    "teamName": "UP",
				    "teamId": "a64bf1d8-18ad-48b6-9820-a0cc6f0458f4",
				    "overallPosition": 2,
				    "id": 1
				}
				

Api endpoints:

1. Get Endponits:

	a. localhost:9092/api/ranking/fetch/{filter}/{value} --> to fetch football standings based on diff filter and its values like 	country,leauge,team.
	b. localhost:9092/api/ranking/{id} --> to fetch particular football standings based on id
	

2. Post Endpoints:

	localhost:9092/api/ranking , @RequestBody Football football --> to add football standings in h2 db
	
2. Put Endpoints:

	localhost:9092/api/ranking , @RequestBody Football football  --> to update football standings in h2 db
	
4. Delete Endpoints:
	
	localhost:9092/api/ranking/{id}  --> to delete particular records in football standings by id from h2 db
	
	
Db:
	h2 db used
	
Note: All api's been secured by spring security

Unit Testing:

Junit testing been done through Mockito framework.
	


<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

#Docker

Steps for building , tagging and pushinh the image to docker hub. 

	1. Create dockerfile.
	
		Dockerfile:
			FROM openjdk:8-jdk-alpine
			MAINTAINER Abu Talha Siddiqi "atalha9@gmail.com"
			VOLUME /tmp
			ADD */target/*App*.jar app1.jar
			ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app1.jar"]

	2. 	Build docker image from directory where dockerfile is present in the project.
	
		docker build .  -t "name:version"
		
	3.  Push to docker hub:
	
		a. docker tag football-ranking:v1 abbiee9/football-ranking:v1
		b. Docker login (giving docker credentials to login)
		c. docker push abbiee9/football-ranking:v1
	
	
	
	
		
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

#Kuberenetes Deploying by using dockerhub image

# Solution 1 ::: ideal solution for seeing the output on localhost/or any other host 


# Use of ingress,service and deployment for output

When user hits the url  the request is intercepted by ingress which looks for the relavent service and later service identifies which pod it need to target based on the selector ( pod is created by the deployment yaml which has the same metadata name as mentioned in service selector) --- deployment picks the image from containerRegistry and deploy it in a cluster( collection of nodes).
	
             
	                
# Config map

Used to take value from environment that is non sensitive.
	
	kind: ConfigMap
	apiVersion: v1
	metadata:
	 name: FootballRankingMsConfigMap
	 namespace: dev
	
	data:
	 security.user.name: user
	 server.port: 9092
	 
To create this component just run following commands:
	kubectl apply -f <location>/configmap.yaml
	                
#Secret	

To store sensitive environment values  like db password etc.     

	apiVersion: v1
	kind: Secret
	metadata:
	  name: security-connection-details
	  namespace: dev
	type: Opaque
	stringData:
	  security.user.password: pass  
	  
To create this component just run following commands:
	kubectl apply -f <location>/secret.yaml
	
# ingress gateway using ambassador
			
Act as a loadBalancer and targets the service using a Mapping yaml	
				
		apiVersion: v1
		kind: Service
		metadata:
		  labels:
		    service: ambassador
		  name: ambassadork8test
		  annotations:
		    getambassador.io/config: |
		      ---
		      apiVersion: ambassador/v0
		      kind:  Mapping
		      name:  FootballRankingMsmapping
		      prefix: /
		      timeout_ms: 60000
		      service: FootballRankingMs:9200
		     
		spec:
		  type: LoadBalancer
		  ports:
		  - name: ambassador
		    port: 80
		    targetPort: 8080
		  selector:
		    service: ambassador	

port no 80 will resolve to localhost 

mapping is used for intelligent routing	, we can prefix condition based on which service will be decided .

ingress is created to expose our kubernetes services to outside world and it acts as a load balancer. 	         

# service
	
type is ClusterIP bcz it is used by ingress and it is not reuired to expose it externally.
	
	apiVersion: v1
	kind: Service
	metadata:
	  name: FootballRankingMs
	  namespace: dev
	spec:
	 type: ClusterIP
	 ports:
	 - port: 9092
	   targetPort: 9092
	   
To create this component just run following commands:
	kubectl apply -f <location>/service.yaml

Service is required to make pod ip static , because pod is not immortal. It can die and will not be resurrected it will always have new identity and k8 make sure that mentioned replica is always maintained.

Service will work as a label to pod, no matter how many times pods gets created label will remain same.	
	
	
    
	    
# deployment
	
		kind: Deployment
		apiVersion: apps/v1beta2
		metadata:
		  name: FootballRankingMs
		  namespace: dev
		  labels:
		    app: FootballRankingMs
		spec:
		  replicas: 1
		  selector:
		    matchLabels:
		      app: FootballRankingMs
		  template:
		    metadata:
		      labels:
		        app: FootballRankingMs
		    spec:
		      containers:
		        - name: FootballRankingMs
		          image: docker.io/abbiee9/FootballRankingMs:v1
		          ports:
		          - name: nfs
		            containerPort: 8500
		            protocol: TCP
		          env:
		            - name: security.user.password
		              valueFrom:
		                secretKeyRef:
		                  name: security-connection-details
		                  key: security.user.password
		            - configMapRef:
		                name: FootballRankingMsConfigMap  
		                
To create this component just run following commands:
	kubectl apply -f <location>/deployment.yaml	
			         
This will create pod in k8 cluster by pulling the image from container registry based on the replicas mentioned in deployment.yaml, pod name will consist of metadata name + some random address.
                
By using service and ingress just use:

	localhost/controllerPath  --- for output	            
	                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
# solution 2 by using deployment and using port-forward 

Note: Without ingress and service we can test our application locally by doing port-forward on the pods but using service and ingress is a ideal way to access the application.

	start kubectl port-forward podname port:port

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

# Solution 3 by just using deployment and exposing the pod 

	1. Create a deployment yaml normally, and deploy it which creates the pod.
	2. Expose your pod by following commands:
			
			a. kubectl expose deployment kubernetestestdeployment --type=LoadBalancer --name=k8service
			
			b. the type must be LoadBalancer bcz it act as a ingress internally
			
			c. this command creates a service and output can be seen by entring localhost/controllerPath
			
			