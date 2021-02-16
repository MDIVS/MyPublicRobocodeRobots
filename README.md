**TheTester v1.0**
> a robot by Maicon Santos.  
> Instagram: maiconoficialbr  
> Facebook: facebook.com/MDIVSbr  
> E-mail: maiconoficialbr@gmail.com  

**----------------- Português BR -----------------------**

**Descrição do robô:**  
A estratégia utilizada é muito simples: esquivar movendo-se a um ângulo entre 45 e 90° em relação ao oponente e atacá-lo no processo. A variação de ângulo possui fins de aproximação e afastamento do adversário. Caso colidir com um projétil, robô ou parede, o TheTester irá inverter a direção de seu movimento na tentativa de evitar se prender em paredes e também evitar ser fácilmente nocauteado por robôs do tipo RamFire.

Pontos fortes:  
O foco desse robô são lutas 1x1 contra bots básicos. De acordo com os meus testes, a tendência de vantagem sobre robôs básicos, isto é, sem algoritmo de MUV ou IA, varia entre 60%~80%.  
  
Pontos fracos:  
É importante notar que esse robô não é tão bom em espaços muito fechados. Quanto maior o espaço melhor ele se desempenha. Existem, ainda dificuldades adicionais em relação aos cantos do campo de batalha que, muito embora muito raro de ocorrer, pode custar a partida.  
  
**Minha experiência com o desenvolvimento desse código:**  
Eu não sou programador de Robocode, geralmente mexo com engines de jogos, tipo o Godot, então pra mim foi algo novo e muito interessante. Fiz apenas para participar do processo seletivo da Solutis, e acabei gostando muito.  
  
O sistema de esquiva que eu implementei no robô veio de um [artigo da IBM](https://www.ibm.com/developerworks/library/j-dodge/j-dodge-pdf.pdf) (último acesso em 23/10/2020) que falava justamente sobre como esquivar poderia ser uma boa estratégia.  
  
O código deles estava meio quebrado, então eu complementei, criei o que chamei de "robô da IBM" e estabelesci a meta de fazer um robô melhor que ele. Eis aqui o The Tester (nome ruim do caramba, eu sei).  
  
Já o sistema de targeting é oriundo dos tutoriais do canal Adam Bignold no YouTube. Mais especificamente [deste](https://youtu.be/4TUwwjP4KNk?list=PLQ4aY5VFhm1WunVVCCsdsS6RC4pWeUJwl) vídeo.
  
**------------------- English ------------------**  
  
**ROBOT DESCRIPTION**  
This robot was projected to win 1x1 games.  
His strategy is too simple: Dodge and attack.
  
**AUTHOR NOTES**  
I'm not a robocode programmer, I just did this to apply for a job.  
  
The dodge system was based in a IBM code that I found [here](https://www.ibm.com/developerworks/library/j-dodge/j-dodge-pdf.pdf) (last access at 23/September/2020). But now if you put my robot to challenge that robot, you will know how I improved this.  
  
The targeting system comes from Adam Bignold tutorials, specifically [this](https://youtu.be/4TUwwjP4KNk?list=PLQ4aY5VFhm1WunVVCCsdsS6RC4pWeUJwl) video.
  
Note that this robot will not be nice at a very closed space or when there is too many robot per arena space because it needs space to dodge.  