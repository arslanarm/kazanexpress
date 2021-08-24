package me.plony.kazanexpress

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName
import java.util.concurrent.Future

class ServiceTestContainer(docker: Future<String>) : GenericContainer<ServiceTestContainer>(docker)