variable "access_key" {
  description = "AWS access key"
}

variable "secret_key" {
  description = "AWS secret access key"
}

variable "region" {
  description = "AWS region to host your network"
  default     = "us-west-2"
}

variable "az1" {
  description = "Availability zone #1"
  default     = "us-west-2a"
}

variable "az2" {
  description = "Availability zone #2"
  default     = "us-west-2b"
}

variable "env" {
  description = "Environment name"
  default     = "dev"
}

variable "vpc_cidr" {
  description = "CIDR for VPC"
  default     = "10.128.0.0/16"
}

variable "public_subnet_cidr" {
  description = "CIDR for public subnet"
  default     = "10.128.0.0/24"
}

variable "private_subnet_cidr" {
  description = "CIDR for private subnet"
  default     = "10.128.1.0/24"
}

/* Ubuntu 16.10 AMIs by region, ebs-ssd */
/* @see https://cloud-images.ubuntu.com/locator/ec2/ */
variable "amis" {
  description = "Base AMI to launch the instances with"
  default = {
    us-west-2 = "ami-26d87c46"
    us-east-1 = "ami-bdc390aa"
  }
}
