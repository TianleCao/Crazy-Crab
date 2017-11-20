package tester;
import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacpp.opencv_core.CvMat;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FlyCapture2FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;
import java.math.*;
public class FaceApp extends Applet implements Runnable {
	public FaceApp() {
	}

    private CvHaarClassifierCascade classifier1 = null;
    private CvHaarClassifierCascade classifier2 = null;
    private CvMemStorage storage = null;
    private FrameGrabber grabber = null;
    private IplImage grabbedImage = null, grayImage = null,smallImage=null, rImage=null;
    private CvSeq faces = null;
    private boolean stop = false;
    private Exception exception = null;
    OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
    Java2DFrameConverter paintConverter = new Java2DFrameConverter();
    private double Angle=0;
    double x_ori,y_ori;
    @Override public void init() {
        try {
            // Load the classifier file from Java resources.
        	URL url = new URL("https://raw.github.com/Itseez/opencv/2.4.0/data/haarcascades/haarcascade_frontalface_alt2.xml");
        	//URL url= new URL("file:/C:/opencv/sources/data/haarcascades/haarcascade_frontalface_alt.xml");
            File classifierFile1 = Loader.extractResource(url, null, "classifier", ".xml");
            url = new URL("https://raw.github.com/Itseez/opencv/2.4.0/data/haarcascades/haarcascade_profileface.xml");
            File classifierFile2 = Loader.extractResource(url, null, "classifier", ".xml");
            if (classifierFile1 == null || classifierFile1.length() <= 0) {
                throw new IOException("Could not extract  from Java resources.");
            }

            // Preload the opencv_objdetect module to work around a known bug.
            Loader.load(opencv_objdetect.class);
            classifier1 = new CvHaarClassifierCascade(cvLoad(classifierFile1.getAbsolutePath()));
            classifier2 = new CvHaarClassifierCascade(cvLoad(classifierFile2.getAbsolutePath()));
            //classifier = new CvHaarClassifierCascade(cvLoad("C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml"));
            classifierFile1.delete();
            classifierFile2.delete();
            if (classifier1.isNull()) {
                throw new IOException("Could not load the classifier file.");
            }

            storage = CvMemStorage.create();
        } catch (Exception e) {
            if (exception == null) {
                exception = e;
                repaint();
            }
        }
        System.out.println("loaded");
    }

    @Override public void start() {
        try {
            new Thread(this).start();
        } catch (Exception e) {
            if (exception == null) {
                exception = e;
                repaint();
            }
        }
    }

    public void run() {
        try {
            try {
                grabber = FrameGrabber.createDefault(0);
                grabber.setImageWidth(getWidth());
                grabber.setImageHeight(getHeight());
                System.out.println(getWidth()+" "+getHeight());
                grabber.start();
                grabbedImage = grabberConverter.convert(grabber.grab());
            } catch (Exception e) {
                if (grabber != null) grabber.release();
                grabber = new OpenCVFrameGrabber(0);
                grabber.setImageWidth(getWidth());
                grabber.setImageHeight(getHeight());
                
                grabber.start();
                grabbedImage = grabberConverter.convert(grabber.grab());
            }
            grayImage  = IplImage.create(grabbedImage.width(),   grabbedImage.height(),   IPL_DEPTH_8U, 1);
            smallImage = IplImage.create(grabbedImage.width()/2, grabbedImage.height()/2, IPL_DEPTH_8U, 1);
            stop = false;
            while (!stop && (grabbedImage = grabberConverter.convert(grabber.grab())) != null) {
                if (faces == null) {
                    cvClearMemStorage(storage);
                    cvCvtColor(grabbedImage, grayImage, CV_BGR2GRAY);
                    cvResize(grayImage, smallImage, CV_INTER_AREA);
                    long time1=System.currentTimeMillis();
                    faces = cvHaarDetectObjects(smallImage, classifier1, storage,1.1, 3,
                    		//CV_HAAR_DO_CANNY_PRUNING);
                    		CV_HAAR_FIND_BIGGEST_OBJECT | CV_HAAR_DO_ROUGH_SEARCH);
                    int n=0;
                    int num=0;
                    int angle=0;
                    boolean clockwise=false;
                    while((num==0)&&(n<=10)){
                    	rImage=rotateImage(smallImage,angle,clockwise);
                    faces = cvHaarDetectObjects(rImage, classifier1, storage, 1.1, 3,
                          // CV_HAAR_FIND_BIGGEST_OBJECT | CV_HAAR_DO_ROUGH_SEARCH);
                    		CV_HAAR_DO_CANNY_PRUNING);
                    n++;
                    angle=3*n;
                    //angle=(int)(5*(int)((n+1)/2)*Math.pow(-1, n))+PastAngle;
                   // if (angle>0) clockwise=true;
                    	//else clockwise=false;
                    num=faces.total();
                    }
                   long time2=System.currentTimeMillis();
                    if (num!=0)
                    {
                    	//angle=(int)(3*(int)((n)/2)*Math.pow(-1, n-1))+PastAngle;
                    	//PastAngle=angle;
                    	//System.out.println((time2-time1)+"ms passed, face found at"+(angle-3));
                    	angle=angle-3;
                    	CvRect r = new CvRect(cvGetSeqElem(faces, 1));
                    	//x_ori=Math.cos(angle*CV_PI/180)*(r.x()+1/2*r.width()-rImage.width()/2)+Math.sin(angle*CV_PI/180)*(r.y()+1/2*r.height()-rImage.height());
                    	//y_ori=-Math.sin(angle*CV_PI/180)*(r.x()+1/2*r.width()-rImage.width()/2)+Math.cos(angle*CV_PI/180)*(r.y()+1/2*r.height()-rImage.height());
                    	//Angle=1/CV_PI*180*Math.atan((x_ori-rImage.width()/2)/(rImage.height()/2+smallImage.height()/2-y_ori));
                    	if (!clockwise){
                    		x_ori=Math.cos(angle*CV_PI/180)*23+Math.sin(angle*CV_PI/180)*smallImage.height();
                    		y_ori=rImage.height()-23*Math.sin(angle*CV_PI/180);
                    		Angle=180*Math.atan2(y_ori-(r.y()+1/2*r.height()),r.x()+1/2*r.width()-x_ori)/CV_PI-angle;
                    	}
                    	else 
                    	{
                    		x_ori=Math.cos(angle*CV_PI/180)*23;
                    		//y_ori=rImage.height()-(grabbedImage.width()-23)*Math.sin(angle*CV_PI/180);
                    		y_ori=grabbedImage.height()*Math.cos(angle*CV_PI/180)+Math.sin(angle*CV_PI/180)*23;
                    		double temp=180*Math.atan2(y_ori-(r.y()+1/2*r.height()),r.x()+1/2*r.width()-x_ori)/CV_PI-angle;
                    		//if (temp>=90)
                    		Angle=temp;
                    	}
                    	
                    	//double delta_Angle=180*Math.atan((r.x()+1/2*r.width()-rImage.width()/2)/(r.y()+1/2*r.height()-rImage.height()))/CV_PI;
                    	System.out.print((time2-time1)+"ms passed, face found at ");
                    	System.out.println(Angle);
                    	System.out.println(x_ori+" "+y_ori);
                    	System.out.println((r.x()+1/2*r.width())+" "+(r.y()+1/2*r.height()));
                    	System.out.println(rImage.width()+" "+rImage.height());
                    	System.out.println(smallImage.width()+" "+smallImage.height());
                    }
                    else 
                    {
                    	//PastAngle=PastAngle;
                    	System.out.println((time2-time1)+"ms passed, no face found");
                    }
                    //if ((angle!=3)&&(num>0)) System.out.println("Faces detected at "+(angle-3));*/
                    repaint();
                }
            }
            grabbedImage = grayImage =smallImage=rImage= null;
            grabber.stop();
            grabber.release();
            grabber = null;
        } catch (Exception e) {
            if (exception == null) {
                exception = e;
                repaint();
            }
        }
    }
    public IplImage rotateImage(IplImage src, int angle, boolean clockwise)  
    {  
        angle = abs(angle) % 180;  
        if (angle > 90)  
        {  
            angle = 90 - (angle % 90);  
        }  
        IplImage dst = null;  
        int width =  
            (int) (src.height()* Math.sin(angle * CV_PI / 180.0) +  
		src.width()* Math.cos(angle * CV_PI / 180.0 ) + 1);  
        int height =  
            (int) (src.height() * Math.cos(angle * CV_PI / 180.0) +  
		src.width() * Math.sin(angle * CV_PI / 180.0 ) + 1);  
        int tempLength = (int) (Math.sqrt(src.width() * src.width() + src.height() * src.height()) + 10);  
        int tempX = (tempLength + 1) / 2 - src.width()/ 2;  
        int tempY = (tempLength + 1) / 2 - src.height()/ 2;  
        int flag = -1;  
      
        dst = cvCreateImage(cvSize(width, height), src.depth(), src.nChannels());  
        cvZero(dst);  
        IplImage temp = cvCreateImage(cvSize(tempLength, tempLength), src.depth(), src.nChannels());  
        cvZero(temp);  
      
        cvSetImageROI(temp, cvRect(tempX, tempY, src.width(), src.height()));  
        cvCopy(src, temp, null);  
        cvResetImageROI(temp);  
        if (clockwise)  
            flag = 1;  
      
        float[] m=new float[6];
        int w = temp.width();  
        int h = temp.height();  
        m[0] = (float) Math.cos(flag * angle * CV_PI / 180.);  
        m[1] = (float) Math.sin(flag * angle * CV_PI / 180.);  
        m[3] = -m[1];  
        m[4] = m[0];  
        // 将旋转中心移至图像中间  
        m[2] = w * 0.5f;  
        m[5] = h * 0.5f;  
        //  
        CvMat M = cvCreateMat(2, 3, CV_32F);  
        cvmSet( M, 0, 0, m[0] );
        cvmSet( M, 0, 1, m[1] );
        cvmSet( M, 0, 2, m[2] );
        cvmSet( M, 1, 0, m[3] );
        cvmSet( M, 1, 1, m[4] );
        cvmSet( M, 1, 2, m[5] );
        
        cvGetQuadrangleSubPix(temp, dst, M);  
        cvReleaseImage(temp);  
        return dst;  
    }  
    @Override public void update(Graphics g) {
        paint(g);
    }

    @Override public void paint(Graphics g) {
        if (grabbedImage != null) {
            Frame frame = grabberConverter.convert(rotateImage(grabbedImage,0,false));
            BufferedImage image = paintConverter.getBufferedImage(frame, 2.2/grabber.getGamma());
            Graphics2D g2 = image.createGraphics();
            if (faces != null) {
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(2));
                int total = faces.total();
                for (int i = 0; i < total; i++) {
                    CvRect r = new CvRect(cvGetSeqElem(faces, i));
                    //g2.drawRect((int)(2*x_ori-r.width()+rImage.width()-smallImage.width()),(int)(2*y_ori-r.height()+rImage.height()-smallImage.height()), r.width()*2, r.height()*2);
                    g2.drawRect(r.x()*2, r.y()*2, r.width()*2, r.height()*2);
                }
                faces = null;
            }
            g.drawImage(image, 0, 0, null);
        }
        if (exception != null) {
            int y = 0, h = g.getFontMetrics().getHeight();
            g.drawString(exception.toString(), 5, y += h);
            for (StackTraceElement e : exception.getStackTrace()) {
                g.drawString("        at " + e.toString(), 5, y += h);
            }
        }
    }

    @Override public void stop() {
        stop = true;
    }
    public double getAngle(){
    	return Angle;
    }
}

