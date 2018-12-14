package com.capgemini.productionline.configuration

import groovy.json.*

/**
 * Contains the configuration methods of the nexus component
 * <p>
 *     The main purpose collecting configuration methods.
 *
 * Created by cdjahan on 14.12.2018.
 */

class Nexus implements Serializable {

  def String adminUser = ""
  def String adminPasswd = ""
  def String nexusHostUrl = ""



  Nexus (adminUser, adminPasswd, nexusHostUrl) {
    this.adminUser = adminUser
    this.adminPasswd = adminPasswd
    this.nexusHostUrl = nexusHostUrl
  }

  public String createMavenRepository (String repoName) {

  def jsonDataSlurper = new JsonSlurper().parseText('{}')

  jsonDataSlurper << [name: 'maven',
  type: 'groovy',
  content: "repository.createMavenHosted('maven-internal')" ];

  def jsonDataOutput = JsonOutput.toJson(jsonDataSlurper)

  def proc = 'curl -v -u ${adminUser}:${adminPasswd} --header "Content-Type: application/json" \'${nexusHostUrl}/service/rest/v1/script/\' -d ${jsonDataOutput}'.execute()

  Thread.start { System.err << proc.err }
  // println proc.err.text
  // println proc.text

  return proc

  // proc.waitFor()

  }

}
