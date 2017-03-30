def userId = args[0]
def password = args[1]
def instance = jenkins.model.Jenkins.instance
def exist = instance.securityRealm.allUsers.find {it.id == userId}
if (exist == null) {
    instance.securityRealm.createAccount(userId, password)
    println(1)
} else {
    println(0)
}
