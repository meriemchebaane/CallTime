const util = require('util');
const exec = util.promisify(require('child_process').exec);
var schedule = require('node-schedule');
//var semaine = ["lundi","mardi","merecredi","jeudi","vendredi","samedi","dimanche"]
var semaine = ["dimanche"]
var slot =["00:00","08:00","12:00","14:00","18:00","23:59"];
async function populate(jour,start_time, end_time) {
 await exec('nodejs CallTime.js '+jour+' '+start_time+' '+end_time);

}
semaine.forEach(jour => {
    //setTimeout(populate_day, 30000);
  
    for (let index = 0; index < slot.length; index++) {
        if(slot[index]!="23:59")
        {
          
                populate(jour,slot[index],slot[index+1]);
           
            
        }
    }
});
var j = schedule.scheduleJob({dayOfWeek: 7}, function(){
    semaine.forEach(jour => {
        //setTimeout(populate_day, 30000);
      
        for (let index = 0; index < slot.length; index++) {
            if(slot[index]!="23:59")
            {
              
                    populate(jour,slot[index],slot[index+1]);
               
                
            }
        }
    });
  });

