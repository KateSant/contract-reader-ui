terraform {
  
  required_providers {
    google = {
      source = "hashicorp/google"
    }
  }
  cloud {
    organization = "kate-dev"

    workspaces {
      tags = ["contractreader"]
    }
  }
}


provider "google" {
  project     = "contract-reader"
  region      = "europe-west4"
}

variable "service-name" {
  type = string
}

variable "docker-image" {
  type = string
}

variable "engine-endpoint" {
  type = string
}

variable "oauth-github-secret" {
  type = string
}


resource "google_cloud_run_service" "cluster" {
  name     = var.service-name
  location = "europe-west4"

  template {
    spec {
      containers {
        image = var.docker-image
        env {
          name = "contract-reader_engine_url"
          value = var.engine-endpoint
        }
        env {
          name = "spring_security_oauth2_client_registration_github_clientSecret"
          value = var.oauth-github-secret
        }
      }

    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
  autogenerate_revision_name = true
}

data "google_iam_policy" "noauth" {
  binding {
    role = "roles/run.invoker"
    members = [
      "allUsers",
    ]
  }
}

resource "google_cloud_run_service_iam_policy" "noauth" {
  location    = google_cloud_run_service.cluster.location
  project     = google_cloud_run_service.cluster.project
  service     = google_cloud_run_service.cluster.name

  policy_data = data.google_iam_policy.noauth.policy_data
}
