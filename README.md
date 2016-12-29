# monbox
Cloud monitoring service on AWS utilizing Spring Boot, Spring Cloud, AWS SDK, Prometheus, Grafana, Docker and Terraform.  Heavily borrowed from [Hygieia](https://github.com/capitalone/Hygieia)'s AWS Cloud [collector](https://github.com/capitalone/Hygieia/tree/master/collectors/cloud/aws) but employs Redis (or ElastiCache) for persistence.


This is a [Spring Boot](http://projects.spring.io/spring-boot/) application.  

## Prerequisites

* An [AWS](https://aws.amazon.com) account. You may follow instructions to create one [here](http://docs.aws.amazon.com/AmazonSimpleDB/latest/DeveloperGuide/AboutAWSAccounts.html).
* [aws-cli](http://docs.aws.amazon.com/cli/latest/userguide/installing.html) 1.11.34 or better
* [Docker Toolbox](http://docs.docker.com/mac/started/); or `docker`, `docker-machine` and `docker-compose` are required
* Java [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 1.8.0_112 or better
* [Maven](https://maven.apache.org/download.cgi) 3.3.9 or better


## Prepare AWS CLI

See 

* [aws configure](http://docs.aws.amazon.com/cli/latest/reference/configure/)
* [Configuring the AWS Command Line Interface](http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html)

You must configure a `default` profile, e.g., 

```
$ aws configure --profile default
```
 
## How to build

### Clone

#### with HTTPS

```
git clone https://github.com/fastnsilver/monbox.git
```

#### with SSH

```
git clone git@github.com:fastnsilver/monbox.git
```

### with Maven

```
$ mvn clean verify
```

### with Jenkinsfile

Pipeline support to be designed


## Prepare to work with Redis 

This service interacts with a Redis instance.  Assumes instance is up-and-running at localhost (127.0.0.1). If you want to change that then you need to add the following argument (when attempting to run the service)

```
-Dspring.redis.host={redis.host}
```

where `{redis.host}` is the hostname or IP address of the Redis instance

You could start an instance of Redis with Docker using

```
docker run -p6379:6379 -d redis:3.2.6-alpine
```
To get the IP address of the Docker host

```
docker-machine ip {machine.name}
```

where `{machine.name}` is name of the Docker machine.

To tear down

```
docker ps
docker kill {container.id}
docker rm {container.id}
```

where `{container.id}` is the id of the running Redis container.

### ElastiCache support

* [What is ElastiCache?](http://docs.aws.amazon.com/AmazonElastiCache/latest/UserGuide/WhatIs.html)
* [When Should I Use ElastiCache?](http://docs.aws.amazon.com/AmazonElastiCache/latest/UserGuide/UseCases.html)
* [Managing ElastiCache](http://docs.aws.amazon.com/AmazonElastiCache/latest/UserGuide/WhatIs.Managing.html)

#### Off-cloud

From [Accessing ElastiCache Resources from Outside AWS](http://docs.aws.amazon.com/AmazonElastiCache/latest/UserGuide/Access.Outside.html)

> for testing and development purposes only. It is not recommended for production use.

Running a local instance of this service requires that you provision a [NAT instance](http://docs.aws.amazon.com/AmazonVPC/latest/UserGuide/VPC_NAT_Instance.html) or configure a [NAT Gateway](http://docs.aws.amazon.com/AmazonVPC/latest/UserGuide/vpc-nat-gateway.html).  See [Comparison of NAT Instances and NAT Gateways](http://docs.aws.amazon.com/AmazonVPC/latest/UserGuide/vpc-nat-comparison.html).

Assign an [Elastic IP](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/elastic-ip-addresses-eip.html) to the NAT instance or NAT Gateway and use that IP address as the value for the Redis host. 


#### On AWS

> configure an ElastiCache cluster in an Amazon VPC 

See [(Amazon VPC) with ElastiCache](http://docs.aws.amazon.com/AmazonElastiCache/latest/UserGuide/AmazonVPC.html)

Each node within a cluster will have an endpoint addressable at 

```
{node.name}.{cluster.id}.{region.id}.cache.amazonaws.com:6379
```

Once the cluster is configured and available make sure to set the Redis host environment variable. 

This can be done e.g., 

##### when running service on an EC2 instance, single JVM

by supplying a command-line argument `-Dspring.redis.host`

##### when running as a task on an ECS cluster 

by supplying an environment variable, `SPRING_REDIS_HOST`, within a [task definition](http://docs.aws.amazon.com/AmazonECS/latest/developerguide/task_defintions.html)


## How to Run

### with Spring Boot

```
$ mvn spring-boot:run -Dspring.profiles.active=local -Djava.security.egd=file:/dev/./urandom
```

### with Java

```
$ java -jar monbox-x.x.x.jar -Dspring.profiles.active=local -Djava.security.egd=file:/dev/./urandom
```

### with Docker

Assuming you have installed VirtualBox, Docker Machine, Docker Compose and Docker.

If not, it's highly recommended (on a Mac) to install each via [Homebrew](http://brew.sh/) with

```
brew tap caskroom/cask
brew install brew-cask
brew cask install virtualbox

brew install docker-machine
brew install docker-compose
brew install docker
```

The instruction below provisions a Docker host named `dev` with 2 CPU, 10Gb RAM and 20Gb disk space

```
docker-machine create --driver virtualbox --virtualbox-cpu-count "2" --virtualbox-disk-size "20000" --virtualbox-memory "10240" dev
```

You could also execute the following script which will perform the first step above on your behalf

```
./provision.sh {1}
```

where `{1}` above would be replaced with whatever you want to name your docker-machine

Caveat: You should have at least 12GB of memory and 25GB of disk space on your laptop or workstation.


To begin using it (e.g., where machine name was `dev`)

```
eval $(docker-machine env dev)
```


Lastly, to destroy your docker machine, you could execute

```
./destroy.sh {1}
```

where `{1}` above would be replaced with an existing docker-machine name

Caution! This will remove the VM hosting all your Docker images.


## Docker notes

### Build image

```
./build.sh
```


### Publish image

Assumes proper authentication credentials have been added to `$HOME/.m2/settings.xml`. See:

* [Authenticating with Private Registries](https://github.com/spotify/docker-maven-plugin#authenticating-with-private-registries)

```
mvn clean install -DpushImage
```


### Pull image

TBD


### Run image

```
./startup.sh
```


#### Running a local development environment

See [Running localhost](https://forums.docker.com/t/using-localhost-for-to-access-running-container/3148)

On a Mac we cannot access running Docker containers from localhost.

After running `docker-machine ip {env}` where `{env}` is your instance of a docker-machine, add an entry in `/etc/hosts` that maps `DOCKER_HOST` IP address to a memorable hostname.


### Work with image

Services are accessible via the Docker host (or IP address) and port 

| Service           | Host Port | Container Port |
|-------------------|-----------|----------------|
| Monbox Collector  | 80        | 8080           |
| Redis             | 6379      | 6379           |
| CAdvisor          | 9080      | 8080           |

Visit e.g., `http://192.168.99.100/mappings`

### Stop image (and remove it)

```
./shutdown.sh
```


## EC2 notes

See [Getting Started](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html#ec2-launch-instance_linux) guide. Minimum required instance type is `t2.micro` (which qualifies for free-tier).

Make sure to create a [Key-pair](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-key-pairs.html) and download the private key to a safe location.
Also create an [IAM Role](http://docs.aws.amazon.com/IAM/latest/UserGuide/id_roles.html) with a `ReadOnlyAccess` policy and assign this role to the instance upon creation.  The [Security group](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/security-group-rules-reference.html) should have TCP inbound ports 22 and 8080 open.

Connect to your EC2 instance with

```
ssh -i /path/to/{your-private-key-filename}.pem ec2-user@{public-ip-address-of-instance}
```

Configure an additional YUM repo and install the following packages

```
sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
sudo yum install -y apache-maven java-1.8.0 git
```

Set `JAVA_HOME` and `PATH` in `.bashrc` so that Java 8 is the default

* Change directories and open VI

```
cd ~
vi .bashrc
```

* Add the following lines at the end of the file, save, and exit VI

```
export JAVA_HOME=/usr/lib/jvm/jre-1.8.0-openjdk
export PATH=$JAVA_HOME/bin:$PATH
```

* Update session

```
source ~/.bash_profile
```

* Verify Maven is employing Java 8

```
mvn -version
```


## Test Endpoints

For test purposes only!  Not recommended for large environments with 1000s of compute resources.

| Request | Description |
|---------|-------------|
| GET /cloudInstance?all | |
| GET /cloudInstance/history?all | |
| GET /volume?all | |
| GET /subNetwork?all | |
| GET /virtualNetwork?all | |


## Working with Maven Site 

### Stage

```
mvn site site:stage -Pdocumentation
```

### Publish

Assumes a `gh-pages` (orphan) branch has been set up in advance.  In addition, appropriate authentication credentials have been declared in `$HOME/.m2/settings.xml`. See:

* [Creating Project Pages manually](https://help.github.com/articles/creating-project-pages-manually/)
* [Security and Deployment Settings](http://maven.apache.org/guides/mini/guide-deployment-security-settings.html)

```
mvn scm-publish:publish-scm -Pdocumentation
```