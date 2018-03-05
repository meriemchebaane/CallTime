The deployment should be done following the following steps:

For the android app you just have to download the (.apk) file and install it in your smartphone.

For the server application: The generated container is bulky (at the order of 300 Mo) so in order to regenerate it you will find it in the provided deployments files of the server a docker file which will enable you to create a docker container by following these next steps steps:
•	clone the project into your docker space:
git clone [votre répo]
•	move to the folder on which is situated the Dockerfile
cd [path to your Dockerfile]
•	generate the docker image:
docker build . -t <container_name>
•	start the container from the previously created image:
docker run -p [your port] --name <container_name> <container_name>
•	stop the container 
docker stop <container_name>
•	reboot the container
docker start <container_name>

