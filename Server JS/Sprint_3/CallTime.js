var admin = require("firebase-admin");
var unique = require('array-unique');
var _ = require("underscore");
var unique = require('array-unique');
RandomForestClassifier = require('random-forest-classifier').RandomForestClassifier;
var calls = function(name,number,isCallNew,callType,duration) {
    this.name = name,
	this.number = number,
	this.isCallNew = isCallNew,
    this.callType = callType,
    this.duration = duration
}
var cluster = require('cluster');
if (cluster.isMaster) {
	cluster.fork();
	cluster.on('exit', function(worker, code, signal) {
	  cluster.fork();
	});
  }
var callerStats = function(number,out,inc,miss,duration_out,duration_in){
		this.number = number ;
		this.out = out ;
		this.inc = inc ;
		this.miss = miss ;
		this.duration_out = duration_out;
		this.duration_in = duration_in ;
}
var name = 'name';
var callsList0812 = [];
var callsList0812_filtred = [];
var serviceAccount = require("./calltime2-38aee-firebase-adminsdk-1el3v-136394e0e6.json");
var cnt = 0 ;
var state_cfnumber = false ;
var state_cfname = false ;
var map = {} ;
var argument_s = [];




admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://calltime2-38aee.firebaseio.com"
});
var ref_contatcts = admin.database().ref('contacts');
var ref_calls = admin.database().ref('calls');

process.argv.forEach(function (val, index, array) {
	if(index>=2)
	{
		argument_s.push(val);
	}
  });
 
function function_1(){
 ref_calls.orderByChild("callTime").startAt(argument_s[1]).endAt(argument_s[2]).on("child_added", function(snapshot) {
	 if(snapshot.val().callDayWeek == argument_s[0]){
		callsList0812.push(new calls(snapshot.val().callName, snapshot.val().callNumber,snapshot.val().isCallNew, snapshot.val().callType , snapshot.val().duration));
	    console.log(snapshot.val());
	}
	
});}



function function_2(){

	callsList0812.forEach(element => { console.log(element)});

var type =  0;
for(var i = 0 , len = callsList0812.length ; i < len ; i++){
		if ( callsList0812[i].number != '' ) 
			{ 
			 if(typeof callsList0812[i].name !== 'undefined' )
			 {
				 callsList0812_filtred.push({name : callsList0812[i].name , type : callsList0812[i].callType ,  duration : parseInt(callsList0812[i].duration)}) ;
			 }
			 else
			 {
				//callsList0812_filtred.push({name : callsList0812[i].number , type : callsList0812[i].callType ,  duration : parseInt(callsList0812[i].duration)}) ;


			 }
			}

	//addValueToList ( callsList0812[i].number , callsList0812[i] ) ; 
}


	var rf = new RandomForestClassifier({
		    n_estimators: 100
		});
	var testdata = [];
	var testdata_allContact = [];
	var data = [] ;
	var data_without = [];
	var data_without_allContact = [];
	/* for ( var i =0 , len = callsList0812.length ; i < len ; i++)
		{       
		  if(typeof callsList0812_filtred[i] !== 'undefined' )
			{
			   // console.log(callsList0812_filtred[i]);
				data.push(callsList0812_filtred[i]);
			}
			else
			{
				console.log("index is : "+i);
			}
		}
		data.forEach(item => console.log( item.name));
	for (var i=0; i < 20; i++){
		var ran_num = Math.floor(Math.random() * (data.length - 0 + 1));
		{
			if(typeof callsList0812_filtred[ran_num] !== 'undefined' )
			  {
				
				testdata.push(data[ran_num]);
				data_without = _.without(data, data[ran_num]);
			  }
		      }
	    }
	
	console.log(callsList0812_filtred);
	console.log("~~~~~~~~~~~~~~~~~~~~~~~data~~~~~~~~~~~~~~~~~~~~~~~~~~")
	console.log(data);
	console.log("*************************test data***************************");
	console.log(testdata);
	console.log("######################data without###########################");
	console.log(data_without);
	console.log("######################data filtred by names###########################");
	/*
	var flags = [], output = [], l = testdata.length, i;
		for( i=0; i<l; i++) {
			if( flags[testdata[i].name]) continue;
			flags[testdata[i].name] = true;
			output.push({name : testdata[i].name , score : 0 , occurence : 0});
		}
	*/	
		var flags_2 = [], output_2 = [], l_2 = callsList0812_filtred.length, i_2;
		for( i_2=0; i_2<l_2; i_2++) {
			if( flags_2[callsList0812_filtred[i_2].name]) continue;
			flags_2[callsList0812_filtred[i_2].name] = true;
			output_2.push({name : callsList0812_filtred[i_2].name , score : 0 , occurence : 0 , type : "none"});
		}
		console.log(output_2);	
		
		output_2.forEach(element => {
			var index = _.findLastIndex(callsList0812_filtred, {
				name: element.name
			  });
			var count = 0;
			  for (let i = 0; i < callsList0812.length; i++) {

				  if(callsList0812[i].name == element.name)count++;
				  
			  }
			  console.log("name is : "+element.name+" called : "+count);
			  testdata_allContact.push(callsList0812_filtred[index]) ;
			  if (count!=1)
			  {
				
				callsList0812_filtred = _.without(callsList0812_filtred, callsList0812_filtred[index]) ;
			  }
			  
			
		});
		callsList0812_filtred = _.shuffle(callsList0812_filtred)
		console.log("######################data filtred by allContact###########################");
		//console.log(testdata_allContact);
		output_2.forEach(element => {
			var counter = 0 ;
			for(var i = 0 , len = callsList0812_filtred.length ; i < len ; i++)
			{
				if (callsList0812_filtred[i].name == element.name) counter++;
			}
			element.occurence = counter ;
		});
		console.log("######################output data###########################");
		console.log(output_2);
		//console.log("######################data without###########################");
		//console.log(data_without_allContact);
		console.log("######################data test###########################");
		console.log(testdata_allContact);
		console.log("######################complete###########################");
		console.log(callsList0812_filtred);

		/*
		output.forEach(element => {
			var counter = 0 ;
			for(var i = 0 , len = testdata.length ; i < len ; i++)
			{
				if (testdata[i].name == element.name) counter++;
			}
			element.occurence = counter ;
		});
		console.log("######################output data###########################");
		console.log(output);*/
		

		if (cluster.isWorker) {
			// put your code here
		console.log(data_without_allContact);
		testdata_allContact.forEach(element => {

			var local = [] ;
			local.push(element);
		
				  
			 try {

				rf.fit(callsList0812_filtred, null, "type", function(err, trees){
					var expected =  _.pluck(local, "type");
					var pred = rf.predict(local, trees);	
					console.log("outcome:", pred);
					console.log("expected: ", expected);
					var correct = 0;
					var index = -1 ;
					for (var i=0; i< pred.length; i++){
					index = _.findIndex(output_2,{name: element.name});
					if(index !=-1)
						{
							if (pred[i]==expected[i]){
								//output[testdata.name[i]].score++;
								
								
									output_2[index].score++;
									
								
								correct++;
							}
						output_2[index].type = pred[i];
						}
					}
					
					//console.log(correct + "/" + pred.length, (correct/pred.length)*100 + "%", "accurate");
					//console.log("||||||||||||||||||||||||||||||||||");
					});
				 
			 } catch (error) {
				 
				var expected =  _.pluck(local, "type");
				
				var correct = 0;
				var index = -1 ;
				for (var i=0; i< expected.length; i++){
				index = _.findIndex(output_2,{name: element.name});
				if(index !=-1)
					{
					output_2[index].score++;
					correct++;
					output_2[index].type = expected[i];
					}
			 }
			}


			});
		}
			output_2.forEach(item => console.log( item.name +" with the "+item.type+" type was predicted with "+ (item.score/1)*100 + "%", "accuracy"  ));
			
				var lundi0810 = admin.database().ref().child(argument_s[0]+""+argument_s[1].substring(0, 2)+""+argument_s[2].substring(0, 2));
		
				output_2.forEach(element => {
					var name = element.name;
					lundi0810.child(name).set({
						type: element.type,
						accuracy: element.score,
						occurence:element.occurence
					  });
				});
		 // }


	

    }
     function_1();
     setTimeout(function_2, 4000);

