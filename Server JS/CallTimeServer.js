var admin = require("firebase-admin");

var serviceAccount = require("./calltime2-38aee-firebase-adminsdk-1el3v-136394e0e6.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://calltime2-38aee.firebaseio.com"
});

var ref_contatcts = admin.database().ref('contacts');
var ref_calls = admin.database().ref('calls');

ref_calls.on("value", function(snapshot) {
  console.log(snapshot.val());
}, function (errorObject) {
  console.log("The read failed: " + errorObject.code);
});
