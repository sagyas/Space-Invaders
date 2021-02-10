package game;

import collision.parts.Block;
import collision.parts.Collidable;
import collision.parts.CollisionInfo;
import collision.parts.Paddle;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;

import java.util.List;
import java.util.ArrayList;

/**
 * creates game environemnt with all the collidable.
 */
public class GameEnvironment {
    private List objList = new ArrayList();

    /**
     * add the given collidable to the environment.
     *
     * @param c the object
     */
    public void addCollidable(Collidable c) {
        this.objList.add((Collidable) c);
    }

    /**
     * Assume an object moving from line.start() to line.end(). If this object will
     * not collide with any of the collidables in this collection, return null.
     * Else, return the information about the closest collision that is going to
     * occur.
     *
     * @param trajectory the line of movement
     * @return collisionInfo the object and the point
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List info = new ArrayList();
        /*
         * gets all the objects of the game environment
         */
        for (int i = 0; i < objList.size(); i++) {
            Collidable c = null;
            Rectangle rec = ((Collidable) objList.get(i)).getCollisionRectangle();
            if (((Collidable) objList.get(i)) instanceof Block) {
                c = ((Collidable) objList.get(i));
            } else if (((Collidable) objList.get(i)) instanceof Paddle) {
                c = new Paddle(rec);
            }
            //gets the intersection point with the object
            Point p = trajectory.closestIntersectionToStartOfLine(rec);
            if (p != null) {
                //adds the info to collision info
                CollisionInfo collInfo = new CollisionInfo(p, (Collidable) c);
                info.add((CollisionInfo) collInfo);
            }
        }
        if (info.isEmpty()) {
            return null;
        }
        /*
         * bubble sort of the collision information
         * which one is the closer one
         */
        for (int i = 0; i < info.size() - 1; i++) {
            for (int j = 0; j < info.size() - i - 1; j++) {
                CollisionInfo cInfo1 = (CollisionInfo) info.get(j);
                CollisionInfo cInfo2 = (CollisionInfo) info.get(j + 1);
                Point p1 = cInfo1.collisionPoint();
                Point p2 = cInfo2.collisionPoint();
                if (trajectory.start().compareTo(p1, p2) == -1) {
                    info.set(j + 1, cInfo1);
                    info.set(j, cInfo2);
                }
            }
        }
        //return the closest point
        return ((CollisionInfo) info.get(0));
    }

    /**
     * gets the list of objects.
     *
     * @return list
     */
    public java.util.List getList() {
        return this.objList;
    }
}