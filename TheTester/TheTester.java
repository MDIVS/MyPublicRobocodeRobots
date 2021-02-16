package TheTester_v2;
import java.awt.Color;
import robocode.*;
import robocode.util.Utils;

/* -------------------------------------------
	ROBOT CREDITS

	TheTester v2.0 - a robot by Maicon Santos.
	Instagram: maiconoficialbr
	Facebook: facebook.com/MDIVSbr
	
	<3
*/

public class TheTester_v2 extends AdvancedRobot {
	int movementDirection = 1;
	
	long Aim_time = 0;
	String Aim_name = "";
	double Aim_heading = 0;
	double Aim_bearing = 0;
	double Aim_delta_heading = 0;
	double Aim_energy = 0;
	double Aim_distance = 0;
	double Aim_velocity = 0;
	double Aim_angle = 0;
	double Aim_previousx = 0;
	double Aim_previousy = 0;
	double Aim_x = 0;
	double Aim_y = 0;
	double Aim_nextx = 0;
	double Aim_nexty = 0;
	boolean MRU = true;
	
	// return a x position by direction and angle
	double lengthdir_x( double dist, double angle ) {
		return dist*Math.sin(Math.toRadians(angle));
	}
	
	// return a y position by direction and angle
	double lengthdir_y( double dist, double angle ) {
		return dist*Math.cos(Math.toRadians(angle));
	}
	
	// return a angle between two angles.
	double angle_difference(double angle1, double angle2) {
	    double diff = angle2 - angle1;
		if (Math.abs(diff) < 180) {return diff;}
		else {return diff+(360*-Math.signum(diff));}
	}
	
	// return the line direction with two of your points
	double point_direction(double x1, double y1, double x2, double y2) {
		double a = -Math.toDegrees(Math.atan2(y1 - y2, x1 - x2))-90;
		return (a<-180 ? a+360:a);
	}
	
	// return the distance between two points
	double point_distance(double x1, double y1, double x2, double y2) {
		double a = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		return (a<-180 ? a+360:a);
	}
	
	public void run() {
		// remove body angles influences
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		
		//colorize
		setColors(new Color(0,128,255),Color.black,new Color(0,128,255),new Color(0,128,255),Color.white);
		
		do {
			// ...
			//Turn the radar if we have no mere turns, starts it if it stops and at the start of round
			if (getRadarTurnRemaining()==0.0) {
				setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
			}
			execute();
		} while(true);
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		//get data about enemy
		Aim_time = getTime();
		
		if (Aim_name==e.getName()) {
			// get the direction variation
			Aim_delta_heading = e.getHeading()-Aim_heading;
		}
		
		Aim_name = e.getName();
		Aim_energy = e.getEnergy();
		Aim_bearing = e.getBearing(); //[-180,180]
		Aim_heading = e.getHeading(); //[0,360);
		Aim_velocity = e.getVelocity();
		Aim_distance = e.getDistance();
		Aim_angle = getHeading()+e.getBearing();
		Aim_x = getX()+lengthdir_x(Aim_distance,Aim_angle);
		Aim_y = getY()+lengthdir_y(Aim_distance,Aim_angle);
		
		// ... ENERGY ADMINISTRATION
		// lets considerate the distance,
		double firepower;
		if (Aim_distance>500) {firepower = 1.1;}
		else {firepower = 6*(1-Math.pow(Math.min(1,Aim_distance/500),2));};
		
		// the energy difference to my enemy
		firepower = getEnergy()>Aim_energy?firepower:(firepower-1);
		
		// and the energy itself.
		firepower = getEnergy()>10?firepower:1;
		
		// and others
		firepower = getOthers()>1?Math.min(firepower,1):firepower;
		
		// ... DODGE, APROXIMATON AND KEEP DISTANCE COMPORTMENTS
			
		//keep aproximing at 45° if distance to enemy is >200
		//or get distance to the enemy at 125° if distance to enemy is <200
		//this will give us a nice movimentation and dodge!
		
        if (getTime() % 20 == 0) {
            movementDirection *= -1;
        }
		
		setTurnRight(Aim_bearing+90-45*(Aim_distance>100?1:0)*movementDirection);
		setAhead(500*movementDirection);
		
		// ... RADAR COMPORTMENT
		
		// Absolute angle towards target
		double angleToEnemy = getHeadingRadians()+e.getBearingRadians();
		
		// Subtract current radar heading to get the turn required to face the enemy, be sure it is normalized
		double radarTurn = Utils.normalRelativeAngle(angleToEnemy-getRadarHeadingRadians());
		
		// Distance we want to scan from middle of enemy to either side
		// The 72 is now how many units from the center of the enemy robot it scans.
		double extraTurn = Math.min(Math.atan(72/e.getDistance()),Rules.RADAR_TURN_RATE_RADIANS);
		
		// Adjust the radar turn so it goes that much further in the direction it is going to turn
		// Basically if we were going to turn it left, turn it even more left, if right, turn more right.
		// This allows us to overshoot our enemy so that we get a good sweep that will not slip
		radarTurn += (radarTurn < 0 ? -extraTurn : extraTurn);
		
		// Turn the radar
		setTurnRadarRightRadians(radarTurn);
		
		// ... GUN COMPORTMENT
		
		if (MRU) {
			// get enemy next position by MRU (Movimento Retilíneo Uniforme)
			double t = Aim_distance/Rules.getBulletSpeed(firepower);
			Aim_nextx = Aim_x+lengthdir_x(Aim_velocity*t,Aim_heading);
			Aim_nexty = Aim_y+lengthdir_y(Aim_velocity*t,Aim_heading);
			
			// restringe previsions
			Aim_nextx = Math.min(Math.max(Aim_nextx,13),getBattleFieldWidth()-13);
			Aim_nexty = Math.min(Math.max(Aim_nexty,13),getBattleFieldHeight()-13);
			
			
			// Turn the gun to the next enemy position
			setTurnGunRight(angle_difference(getGunHeading(),point_direction(getX(),getY(),Aim_nextx,Aim_nexty)));
			
			// Switch MRU to normal aim for next time
			//MRU = !MRU;
		}/* else {
			// get enemy moviment circunference center
			double dir = point_direction(Aim_previousx,Aim_previousy,Aim_x,Aim_y);
			double cX = (Aim_previousx+Aim_x)/2+lengthdir_x(
			setTurnGunRight(angle_difference(getGunHeading(),point_direction(getX(),getY(),Aim_x,Aim_y)));
			MRU = !MRU;
		}*/
		
		if (firepower!=0&&getEnergy()>firepower) fire(firepower);
	}
	
	// just invert movement direction for any collision
	public void onHitByRobot(HitByBulletEvent e) {
		movementDirection *= -1;
	}
	
	public void onHitByBullet(HitByBulletEvent e) {
		movementDirection *= -1;
	}
	
	public void onHitWall(HitWallEvent e) {
		movementDirection *= -1;
	}	
}
