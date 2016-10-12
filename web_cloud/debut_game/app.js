(function(){
var app = angular.module('plunker', []);
var donnee = {
  question: 'Qui a participé à la bataille Fromage ?',
  reponse3 : 'Australie',
  reponse1 : 'France',
  reponse2: 'Allemagne',
  pos_bonne_reponse : 3,
}


var donnee1 = {
  question: 'Qui a participé à la bataille Fromage ?',
  reponse3 : 'Australie',
  reponse1 : 'France',
  reponse2: 'Allemagne',
  pos_bonne_reponse : 3,
}


var donnee2 = {
  question: 'Qui a participé à la bataille lait ?',
  reponse3 : 'Egypte',
  reponse1 : 'Maroc',
  reponse2: 'Algérie',
  pos_bonne_reponse : 1,
}

var donnee3 = {
  question: 'Qui a participé à la bataille lait ?',
  reponse3 : 'Egypte',
  reponse1 : 'Maroc',
  reponse2: 'Algérie',
  pos_bonne_reponse : 1,
}

var donnee4 = {
  question: 'Qui a participé à la bataille lait ?',
  reponse3 : 'Egypte',
  reponse1 : 'Maroc',
  reponse2: 'Algérie',
  pos_bonne_reponse : 1,
}

var donnee5 = {
  question: 'Qui a participé à la bataille lait ?',
  reponse3 : 'Egypte',
  reponse1 : 'Maroc',
  reponse2: 'Algérie',
  pos_bonne_reponse : 1,
}

var donnee6 = {
  question: 'Qui a participé à la bataille lait ?',
  reponse3 : 'Egypte',
  reponse1 : 'Maroc',
  reponse2: 'Algérie',
  pos_bonne_reponse : 1,
}

var donnee7 = {
  question: 'Qui a participé à la bataille lait ?',
  reponse3 : 'Egypte',
  reponse1 : 'Maroc',
  reponse2: 'Algérie',
  pos_bonne_reponse : 1,
}

var donnees = [donnee1,donnee2,donnee3,donnee4,donnee5,donnee6,donnee7];
var reponse = "(pas encore définie)";
 
app.controller('madonnee', function(){
  this.madonnee = donnee;
  this.myreponse = 0;
  this.rep = reponse;
  
  this.donne_reponse =function() {
      if(this.myreponse==donnee.pos_bonne_reponse){
        this.rep="juste";      
      }else{
       this.rep="fausse";
      }
  };
});

var hightscore = 0;

app.controller('madonnee_dynamique', function(){
  this.score=0;
  this.i = 0;
  this.rep = reponse;
  this.affiche_form = true;
  this.affiche_res =false;
  this.nb_question=donnees.length;
  this.hightscore=hightscore;
  this.question_suivante =function() {
    this.madonnee = donnees[this.i];
    this.myreponse = 0;
  }
  this.donne_reponse =function() {
    if(this.myreponse!=0){
      this.affiche_form = false;
      this.affiche_res = true;
      if(this.myreponse==donnees[this.i].pos_bonne_reponse){
        this.rep="juste";
        this.score++;
      }else{
       this.rep="fausse";
      }
    }else{
      alert("Sélectionne une réponse blaireau !");
    }
  };
  
  this.suivant_inter =function() {
    this.i=this.i+1;
    if(this.i<donnees.length){
      this.question_suivante();
      this.affiche_form = true;
      this.affiche_res =false;
    }else{
      if(hightscore<this.score){
        this.hightscore=this.score;
      }
      this.affiche_form = false;
      this.affiche_res =false;
      alert('Jeu Terminé ! :) Votre score est :'+this.score+"/"+donnees.length+".");
      this.i=0;
      this.score=0;
      this.affiche_form = true;

    }
  }
  
  this.question_suivante();

});

app.controller('mareponse', function(){
  this.reponse = reponse;
});


})();

