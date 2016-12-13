/* Infrastructure servers */
resource "aws_instance" "infra" {
  count = 1
  ami = "${lookup(var.amis, var.region)}"
  instance_type = "t2.large"
  subnet_id = "${aws_subnet.private.id}"
  security_groups = ["${aws_security_group.default.id}"]
  key_name = "${aws_key_pair.deployer.key_name}"
  source_dest_check = false
  user_data = "${file(\"cloud-config/statsd-influxdb-grafana.yml\")}"
  tags = {
    Name = "monbox-${var.env}-infra-${count.index}"
  }
}

/* Load balancer */
resource "aws_elb" "infra" {
  name = "monbox-infra-elb"
  subnets = ["${aws_subnet.public.id}"]
  security_groups = ["${aws_security_group.default.id}", "${aws_security_group.infra.id}"]
  listener {
    instance_port = 3003
    instance_protocol = "http"
    lb_port = 80
    lb_protocol = "http"
  }
  instances = ["${aws_instance.app.*.id}"]
  tags = {
    Name = "monbox-${var.env}-elb"
  }
}
