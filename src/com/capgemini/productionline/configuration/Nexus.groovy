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

  def proc = ["curl", "-X", "POST", "-H", "Content-Type: application/json", "-d", "${jsonDataOutput}", "${nexusHostUrl}/service/rest/v1/script/"].execute()
  Thread.start { System.err << proc.err }
  // println proc.err.text
  // println proc.text

  return proc

  // proc.waitFor()

  }

}
