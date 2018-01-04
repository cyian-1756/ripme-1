import json
import subprocess
from hashlib import sha256

# This script will:
# - read current version
# - increment patch version
# - update version in a few places
# - insert new line in ripme.json with message
# - build ripme
# - add the hash of the lastest binary to ripme.json

message = input('message: ')

def get_ripme_json():
    with open('ripme.json') as dataFile:
        ripmeJson = json.load(dataFile)
    return ripmeJson

def update_hash(current_hash):
    ripmeJson = get_ripme_json()
    with open('ripme.json', 'w') as dataFile:
        ripmeJson["currentHash"] = current_hash
        print(ripmeJson["currentHash"])
        json.dump(ripmeJson, dataFile, indent=4)

def update_change_list(message):
    ripmeJson = get_ripme_json()
    with open('ripme.json', 'w') as dataFile:
        ripmeJson["changeList"].insert(0, message)
        json.dump(ripmeJson, dataFile, indent=4)


currentVersion = get_ripme_json()["latestVersion"]

print('Current version ' + currentVersion)

versionFields = currentVersion.split('.')
patchCur = int(versionFields[2])
patchNext = patchCur + 1
majorMinor = versionFields[:2]
majorMinor.append(str(patchNext))
nextVersion = '.'.join(majorMinor)

print('Updating to ' + nextVersion)

substrExpr = 's/' + currentVersion + '/' + nextVersion + '/'
subprocess.call(['sed', '-i', '-e', substrExpr, 'src/main/java/com/rarchives/ripme/ui/UpdateUtils.java'])
subprocess.call(['git', 'grep', 'DEFAULT_VERSION.*' + nextVersion,
                 'src/main/java/com/rarchives/ripme/ui/UpdateUtils.java'])

# TODO: use json parsing here instead of regex
substrExpr = 's/\\\"latestVersion\\\": \\\"' + currentVersion + '\\\"/\\\"latestVersion\\\": \\\"' +\
             nextVersion + '\\\"/'
subprocess.call(['sed', '-i', '-e', substrExpr, 'ripme.json'])
subprocess.call(['git', 'grep', 'latestVersion', 'ripme.json'])

# TODO: Don't use regex and shell calls here, use a xml parserd
substrExpr = 's/<version>' + currentVersion + '/<version>' + nextVersion + '/'
subprocess.call(['sed', '-i', '-e', substrExpr, 'pom.xml'])
subprocess.call(['git', 'grep', '<version>' + nextVersion + '</version>', 'pom.xml'])

commitMessage = nextVersion + ': ' + message

subprocess.call(['git', 'add', '-u'])
subprocess.call(['git', 'commit', '-m', commitMessage])
subprocess.call(['git', 'tag', nextVersion])
print("Building ripme")
subprocess.call(["mvn", "clean", "compile", "assembly:single"])
print("Hashing .jar file")
openedFile = open("./target/ripme-{}-jar-with-dependencies.jar".format(nextVersion), "rb")
readFile = openedFile.read()
file_hash = sha256(readFile).hexdigest()
print("Hash is: {}".format(file_hash))
print("Updating hash")
update_hash(file_hash)
update_change_list(commitMessage)