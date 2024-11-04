import org.gradle.internal.impldep.org.jsoup.Connection.Base

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                password = System.getenv("MAPBOX_DOWNLOADS_TOKEN") ?: "sk.eyJ1Ijoia2luZ2pvcmRhbjIwMDAiLCJhIjoiY2xueDV0MWFsMGJudTJrcWs2bGdyaWo5cCJ9.6BD1BnugkOcxfX6MixN07g"
            }
        }
    }
}

rootProject.name = "CarSpotterOPSC7312"
include(":app")
