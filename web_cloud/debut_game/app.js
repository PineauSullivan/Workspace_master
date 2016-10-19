(function(){
  var app = angular.module('plunker', []).controller('madonnee_dynamique', ['$scope','$window',
    function($scope, $window){
    
      $scope.sname='';
      $scope.sscore;
      $scope.sid = 0;
      
      $scope.addScore = function() {
        gapi.client.scoresendpoint.insertscores({
          id : $scope.sid,
          score : $scope.sscore,
          name : $scope.sname
        }).execute(
          function(resp){
            console.log(resp); 
            $scope.scores.push({
              id : $scope.sid,
              score : $scope.sscore,
              name : $scope.sname
            });
          $scope.$apply();   
          });
      }
    
      $scope.listScore = function(){
        gapi.client.scoresendpoint.listscores().execute(
          function(resp){
            $scope.scores = resp.items; 
            $score.$apply();
            console.log(resp);
          });
        
      }
      
      $window.init= function(){
        console.log("window init called");
        var rootApi = "https://1-dot-matroisiemeapp.appspot.com/_ah/api/";
        gapi.client.load('scoresendpoint','v1',function(){
          console.log('score api loaded');
          $scope.listScore();
        },rootApi);
      }

      
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
      
      var donnees = [donnee1,donnee2,donnee3];
      var reponse = "(pas encore définie)";
      this.donne_nom =function() {
        if(this.monnom!==''&&this.monid!==0){//revoir condition !!!!!
          $scope.sname = this.monnom;
          $scope.sid = this.monid;
          this.affiche_connection=false;
          this.affiche_form = true;
          this.affiche_res =false;
        }
      };
      
      var hightscore = 0;
      
      this.score=0;
      this.i = 0;
      this.rep = reponse;
      this.affiche_form = false;
      this.affiche_connection = true;
      this.affiche_res =false;
      this.nb_question=donnees.length;
      this.hightscore=hightscore;
      this.question_suivante =function() {
        this.madonnee = donnees[this.i];
        this.myreponse = 0;
      }
      this.donne_reponse =function() {
        if(this.myreponse!==0){
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
        if(this.monnom===''||this.monid===0){
           this.affiche_connection=true;
            this.affiche_form = false;
            this.affiche_res =false;
        }else{
          this.affiche_connection=false;
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
            $scope.sscore = this.score; 
            $scope.addScore();
            alert('Jeu Terminé ! :) Votre score est :'+this.score+"/"+donnees.length+".");
            this.i=0;
            this.score=0;
            this.affiche_form = false;
            this.affiche_connection=true;
            $scope.sname = ''; 
            $scope.sid = 0; 


          }
        }
      }
      
      this.question_suivante();
  
    }
  ]);
  


})();

