/* Default security group */
resource "aws_security_group" "default" {
  name = "vpc-monbox"
  description = "Default security group that allows inbound and outbound traffic from all instances in the VPC"
  vpc_id = "${aws_vpc.default.id}"

  ingress {
    from_port   = "0"
    to_port     = "0"
    protocol    = "-1"
    self        = true
  }

  egress {
    from_port   = "0"
    to_port     = "0"
    protocol    = "-1"
    self        = true
  }

  tags {
    Name = "default-monbox-sg"
  }
}

/* Security group for the nat server */
resource "aws_security_group" "nat" {
  name = "nat-monbox"
  description = "Security group for nat instances that allows SSH and VPN traffic from internet. Also allows outbound HTTP[S]"
  vpc_id = "${aws_vpc.default.id}"

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 1194
    to_port   = 1194
    protocol  = "udp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 80
    to_port   = 80
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 443
    to_port   = 443
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }


  tags {
    Name = "nat-monbox-sg"
  }
}

/* Security group for the web */
resource "aws_security_group" "web" {
  name = "web-monbox"
  description = "Security group for web that allows web traffic from internet"
  vpc_id = "${aws_vpc.default.id}"

  ingress {
    from_port = 80
    to_port   = 80
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 443
    to_port   = 443
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags {
    Name = "web-monbox-sg"
  }
}

/* Security group for the web */
resource "aws_security_group" "infra" {
  name = "infra-monbox"
  description = "Security group for infra that allows web traffic from internet"
  vpc_id = "${aws_vpc.default.id}"

  ingress {
    from_port = 80
    to_port   = 3003
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags {
    Name = "infra-monbox-sg"
  }
}
