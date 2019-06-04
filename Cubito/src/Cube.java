import java.awt.DisplayMode;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

public class Cube implements GLEventListener {

   public static DisplayMode dm, dm_old;
   private GLU glu = new GLU();
   private float rquad = 0.0f;
      
   @Override
   public void display( GLAutoDrawable drawable ) {
	
      final GL2 gl = drawable.getGL().getGL2();
      gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
      gl.glLoadIdentity();
      gl.glTranslatef( -5f, -5.0f, -8.0f ); 

      // Rotate The Cube On X, Y & Z
      
 for(int i=0;i<=13;i+=1){
      //giving different colors to different sides
      gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f(0+i, 1.0f, -1.0f); // Top Right Of The Quad (Top)
      gl.glVertex3f( -2+i, 1.0f, -1.0f); // Top Left Of The Quad (Top)
      gl.glVertex3f( -2+i, 1.0f, 1.0f ); // Bottom Left Of The Quad (Top)
      gl.glVertex3f( 0+i, 1.0f, 1.0f ); // Bottom Right Of The Quad (Top)
		
      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f( 0+i, -1.0f, 1.0f ); // Top Right Of The Quad
      gl.glVertex3f( -2+i, -1.0f, 1.0f ); // Top Left Of The Quad
      gl.glVertex3f( -2+i, -1.0f, -1.0f ); // Bottom Left Of The Quad
      gl.glVertex3f( 0+i, -1.0f, -1.0f ); // Bottom Right Of The Quad 

      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f( 0+i, 1.0f, 1.0f ); // Top Right Of The Quad (Front)
      gl.glVertex3f( -2+i, 1.0f, 1.0f ); // Top Left Of The Quad (Front)
      gl.glVertex3f( -2+i, -1.0f, 1.0f ); // Bottom Left Of The Quad
      gl.glVertex3f( 0+i, -1.0f, 1.0f ); // Bottom Right Of The Quad 

      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f( 0+i, -1.0f, -1.0f ); // Bottom Left Of The Quad
      gl.glVertex3f( -2+i, -1.0f, -1.0f ); // Bottom Right Of The Quad
      gl.glVertex3f( -2+i, 1.0f, -1.0f ); // Top Right Of The Quad (Back)
      gl.glVertex3f( 0+i, 1.0f, -1.0f ); // Top Left Of The Quad (Back)

      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f( -2+i, 1.0f, 1.0f ); // Top Right Of The Quad (Left)
      gl.glVertex3f( -2+i, 1.0f, -1.0f ); // Top Left Of The Quad (Left)
      gl.glVertex3f( -2+i, -1.0f, -1.0f ); // Bottom Left Of The Quad
      gl.glVertex3f( -2+i, -1.0f, 1.0f ); // Bottom Right Of The Quad 

      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f( 0+i, 1.0f, -1.0f ); // Top Right Of The Quad (Right)
      gl.glVertex3f( 0+i, 1.0f, 1.0f ); // Top Left Of The Quad
      gl.glVertex3f( 0+i, -1.0f, 1.0f ); // Bottom Left Of The Quad
      gl.glVertex3f( 0+i, -1.0f, -1.0f ); // Bottom Right Of The Quad
      gl.glEnd(); // Done Drawing The Quad
      gl.glFlush();
      //////////////////////////////////////////////////////////////////////
       for(int j=-18;j<=0;j+=4){
           
              gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f(-1+i, 1.0f, -1+j); // Top Right Of The Quad (Top)
      gl.glVertex3f( -3+i, 1.0f, -1+j); // Top Left Of The Quad (Top)
      gl.glVertex3f( -3+i, 1.0f, 1+j ); // Bottom Left Of The Quad (Top)
      gl.glVertex3f( -1+i, 1.0f, 1+j ); // Bottom Right Of The Quad (Top)
		
      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f( -1+i, -1.0f, 1+j ); // Top Right Of The Quad
      gl.glVertex3f( -3+i, -1.0f, 1+j ); // Top Left Of The Quad
      gl.glVertex3f( -3+i, -1.0f, -1+j ); // Bottom Left Of The Quad
      gl.glVertex3f( -1+i, -1.0f, -1+j ); // Bottom Right Of The Quad 

      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f( -1+i, 1.0f, 1+j ); // Top Right Of The Quad (Front)
      gl.glVertex3f( -3+i, 1.0f, 1+j ); // Top Left Of The Quad (Front)
      gl.glVertex3f( -3+i, -1.0f, 1+j ); // Bottom Left Of The Quad
      gl.glVertex3f( -1+i, -1.0f, 1+j ); // Bottom Right Of The Quad 

      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f( -1+i, -1.0f, -1+j ); // Bottom Left Of The Quad
      gl.glVertex3f( -3+i, -1.0f, -1+j ); // Bottom Right Of The Quad
      gl.glVertex3f( -3+i, 1.0f, -1+j ); // Top Right Of The Quad (Back)
      gl.glVertex3f( -1+i, 1.0f, -1+j ); // Top Left Of The Quad (Back)

      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f( -3+i, 1.0f, 1+j ); // Top Right Of The Quad (Left)
      gl.glVertex3f( -3+i, 1.0f, -1+j ); // Top Left Of The Quad (Left)
      gl.glVertex3f( -3+i, -1.0f, -1+j ); // Bottom Left Of The Quad
      gl.glVertex3f( -3+i, -1.0f, 1+j ); // Bottom Right Of The Quad 

      gl.glColor3f(1f,1f,1f); //white color
      gl.glVertex3f( -1+i, 1.0f, -1+j ); // Top Right Of The Quad (Right)
      gl.glVertex3f( -1+i, 1.0f, 1+j ); // Top Left Of The Quad
      gl.glVertex3f( -1+i, -1.0f, 1+j ); // Bottom Left Of The Quad
      gl.glVertex3f( -1+i, -1.0f, -1+j ); // Bottom Right Of The Quad
      gl.glEnd(); // Done Drawing The Quad
      gl.glFlush();
       
       }
 
      
 }
      //////////////////////////////////////////////////////////////////////
      for(int j=0;j<=12;j+=4){
       gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
      gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f(-1+j, 1.0f, -1.0f); // Top Right Of The Quad (Top)
      gl.glVertex3f( -3+j, 1.0f, -1.0f); // Top Left Of The Quad (Top)
      gl.glVertex3f( -3+j, 1.0f, 1.0f ); // Bottom Left Of The Quad (Top)
      gl.glVertex3f( -1+j, 1.0f, 1.0f ); // Bottom Right Of The Quad (Top)
		
     gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f( -1+j, -1.0f, 1.0f ); // Top Right Of The Quad
      gl.glVertex3f( -3+j, -1.0f, 1.0f ); // Top Left Of The Quad
      gl.glVertex3f( -3+j, -1.0f, -1.0f ); // Bottom Left Of The Quad
      gl.glVertex3f( -1+j, -1.0f, -1.0f ); // Bottom Right Of The Quad 

     gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f( -1+j, 1.0f, 1.0f ); // Top Right Of The Quad (Front)
      gl.glVertex3f( -3+j, 1.0f, 1.0f ); // Top Left Of The Quad (Front)
      gl.glVertex3f( -3+j, -1.0f, 1.0f ); // Bottom Left Of The Quad
      gl.glVertex3f( -1+j, -1.0f, 1.0f ); // Bottom Right Of The Quad 

     gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f( -1+j, -1.0f, -1.0f ); // Bottom Left Of The Quad
      gl.glVertex3f( -3+j, -1.0f, -1.0f ); // Bottom Right Of The Quad
      gl.glVertex3f( -3+j, 1.0f, -1.0f ); // Top Right Of The Quad (Back)
      gl.glVertex3f( -1+j, 1.0f, -1.0f ); // Top Left Of The Quad (Back)

     gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f( -1+j, 1.0f, 1.0f ); // Top Right Of The Quad (Left)
      gl.glVertex3f( -1+j, 1.0f, -1.0f ); // Top Left Of The Quad (Left)
      gl.glVertex3f( -1+j, -1.0f, -1.0f ); // Bottom Left Of The Quad
      gl.glVertex3f( -1+j, -1.0f, 1.0f ); // Bottom Right Of The Quad 

    gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f( -3+j, 1.0f, -1.0f ); // Top Right Of The Quad (Right)
      gl.glVertex3f( -3+j, 1.0f, 1.0f ); // Top Left Of The Quad
      gl.glVertex3f( -3+j, -1.0f, 1.0f ); // Bottom Left Of The Quad
      gl.glVertex3f( -3+j, -1.0f, -1.0f ); // Bottom Right Of The Quad
      gl.glEnd(); // Done Drawing The Quad
      gl.glFlush();
      
      /////////////////////////////////////////////////////////////////////////////
      
      for(int i=-18;i<=0;i+=4){
          
            gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
      gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f(1+j, 1.0f, -1+i); // Top Right Of The Quad (Top)
      gl.glVertex3f( -1+j, 1.0f, -1+i); // Top Left Of The Quad (Top)
      gl.glVertex3f( -1+j, 1.0f, 1+i ); // Bottom Left Of The Quad (Top)
      gl.glVertex3f( 1+j, 1.0f, 1+i ); // Bottom Right Of The Quad (Top)
		
     gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f( 1+j, -1.0f, 1+i ); // Top Right Of The Quad
      gl.glVertex3f( -1+j, -1.0f, 1+i ); // Top Left Of The Quad
      gl.glVertex3f( -1+j, -1.0f, -1+i ); // Bottom Left Of The Quad
      gl.glVertex3f( 1+j, -1.0f, -1+i ); // Bottom Right Of The Quad 

     gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f( 1+j, 1.0f, 1+i ); // Top Right Of The Quad (Front)
      gl.glVertex3f( -1+j, 1.0f, 1+i ); // Top Left Of The Quad (Front)
      gl.glVertex3f( -1+j, -1.0f, 1+i ); // Bottom Left Of The Quad
      gl.glVertex3f( 1+j, -1.0f, 1+i ); // Bottom Right Of The Quad 

     gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f( 1+j, -1.0f, -1+i ); // Bottom Left Of The Quad
      gl.glVertex3f( -1+j, -1.0f, -1+i ); // Bottom Right Of The Quad
      gl.glVertex3f( -1+j, 1.0f, -1+i ); // Top Right Of The Quad (Back)
      gl.glVertex3f( 1+j, 1.0f, -1+i ); // Top Left Of The Quad (Back)

     gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f( -1+j, 1.0f, 1+i ); // Top Right Of The Quad (Left)
      gl.glVertex3f( -1+j, 1.0f, -1+i ); // Top Left Of The Quad (Left)
      gl.glVertex3f( -1+j, -1.0f, -1+i ); // Bottom Left Of The Quad
      gl.glVertex3f( -1+j, -1.0f, 1+i ); // Bottom Right Of The Quad 

    gl.glColor3f(0f,0f,1f); //red color
      gl.glVertex3f( 1+j, 1.0f, -1+i ); // Top Right Of The Quad (Right)
      gl.glVertex3f( 1+j, 1.0f, 1+i ); // Top Left Of The Quad
      gl.glVertex3f( 1+j, -1.0f, 1+i ); // Bottom Left Of The Quad
      gl.glVertex3f( 1+j, -1.0f, -1+i ); // Bottom Right Of The Quad
      gl.glEnd(); // Done Drawing The Quad
      gl.glFlush();
      
      
      }
      
     
      
      
      }
 
		
      rquad -= 0.15f;
   }
   
   @Override
   public void dispose( GLAutoDrawable drawable ) {
      // TODO Auto-generated method stub
   }
   
   @Override
   public void init( GLAutoDrawable drawable ) {
	
      final GL2 gl = drawable.getGL().getGL2();
      gl.glShadeModel( GL2.GL_SMOOTH );
      gl.glClearColor( 0f, 0f, 0f, 0f );
      gl.glClearDepth( 1.0f );
      gl.glEnable( GL2.GL_DEPTH_TEST );
      gl.glDepthFunc( GL2.GL_LEQUAL );
      gl.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST );
   }
      
   @Override
   public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height ) {
	
      // TODO Auto-generated method stub
      final GL2 gl = drawable.getGL().getGL2();
      
			
      final float h = ( float ) width / ( float ) height;
      gl.glViewport( 0, 0, width, height );
      gl.glMatrixMode( GL2.GL_PROJECTION );
      gl.glLoadIdentity();
		
      glu.gluPerspective( 90.0f, h, 1.0, 20.0 );
      gl.glMatrixMode( GL2.GL_MODELVIEW );
      gl.glLoadIdentity();
   }
      
   public static void main( String[] args ) {
	
      final GLProfile profile = GLProfile.get( GLProfile.GL2 );
      GLCapabilities capabilities = new GLCapabilities( profile );
      
      // The canvas
      final GLCanvas glcanvas = new GLCanvas( capabilities );
      Cube cube = new Cube();
		
      glcanvas.addGLEventListener( cube );
      glcanvas.setSize( 800, 800 );
		
      final JFrame frame = new JFrame ( " tablero ajedrez" );
      frame.getContentPane().add( glcanvas );
      frame.setSize( frame.getContentPane().getPreferredSize() );
      frame.setVisible( true );
      final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true);
		
      animator.start();
   }
	
}