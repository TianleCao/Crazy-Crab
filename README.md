# Crazy-Crab
Try a somatosensory game made with JavaCV!

![Alt text](/imgs/game.jpg)

## Requirements
You will need Java JDK 1.6 and JavaCV 1.0
## References
> Adamson, Chris, and Joshua Marinacci. "Swing Hacks." (2005). (for ideas to hack the swing component)  
> http://joshuaxiao.iteye.com/blog/710428 (for his UI code and part of the image materials)  
> Romain Guy's Infinite Progress Panel
## Intorduction
This project is aimed for a game controlled by the head's motion. The software would open the camera of PC/laptop and detect the face with JavaCV.
## Code Structure
*src*: source code  
-- element: all the elements used in the game (i.e. ball, brick, music, items)  
-- frame : the main frame of the game  
-- helper : the interface to packages including JavaCV and JDBC  
-- model : the Swing components hacked  
-- tester : testing code, could be ignored  
*material*: contain all the image/audio used in the game (Note there should be a file named 2.wav, but it wasn't uploaded because of file size)
## Demo Results
After lauching the game (run LoginFrame), you can choose to log in or sign up by clicking the words (as shown below).

![Alt text](/imgs/login.jpg)  

The game would start after loading the camera. The music would automatically play in the meantime and could be closed using the second button. Players can choose to start/stop the game by clicking the first button, while the third and fourth one could be used to restart and quit the game. Some of the bricks would give additional tools/props, whose use can be easily inferred from the icon.  

![Alt text](/imgs/gameon.jpg)  

