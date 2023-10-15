# What does this application do?

This application is a simple UI consuming json from the companion project: [contract-reader-engine](https://github.com/KateSant/contract-reader-engine/tree/main). A demo is deployed at: https://contracthub.thinktalkbuild.com  

It is built in thymeleaf for server-side rendering. 

It is protected by OAuth2 federated login.


# Tech Stack

* Java
* Spring Security
* Docker
* Terraform
* Google Cloud Platform, GCR
* Github Actions

# Useful links

* GCP console: https://console.cloud.google.com/run?project=contract-ui
* Github Action workflow: https://github.com/KateSant/contract-reader-ui/actions/workflows/deploy-prod-env.yml
* Terraform Cloud: https://app.terraform.io/app/kate-dev/workspaces/gcp-contractreader-ui

