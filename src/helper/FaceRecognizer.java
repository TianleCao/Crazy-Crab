package helper;

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
public class FaceRecognizer implements Runnable{
	private CvHaarClassifierCascade classifier = null;
    private CvMemStorage storage = null;
    private FrameGrabber grabber = null;
    private IplImage grabbedImage = null, grayImage = null,smallImage=null,rImage=null;
    private CvSeq faces = null;
    private boolean stop = false;
    private Exception exception = null;
    OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
    Java2DFrameConverter paintConverter = new Java2DFrameConverter();
    double Angle=0;
    boolean clock=false;
    double x_ori,y_ori;
    public FaceRecognizer(){
        try {
            // Load the classifier file from Java resources.
        	URL url = new URL("https://raw.github.com/Itseez/opencv/2.4.0/data/haarcascades/haarcascade_frontalface_alt.xml");
            File classifierFile = Loader.extractResource(url, null, "classifier", ".xml");
            if (classifierFile == null || classifierFile.length() <= 0) {
                throw new IOException("Could not extract  from Java resources.");
            }

            // Preload the opencv_objdetect module to work around a known bug.
            Loader.load(opencv_objdetect.class);
            classifier = new CvHaarClassifierCascade(cvLoad(classifierFile.getAbsolutePath()));
            classifierFile.delete();
            if (classifier.isNull()) {
                throw new IOException("Could not load the classifier file.");
            }

            storage = CvMemStorage.create();
        } catch (Exception e) {
            if (exception == null) {
                exception = e;
                //repaint();
            }
        }
        try {
        try {
            grabber = FrameGrabber.createDefault(0);
            grabber.setImageWidth(200);
            grabber.setImageHeight(200);
            grabber.start();
            grabbedImage = grabberConverter.convert(grabber.grab());
        } catch (Exception e) {
            if (grabber != null) grabber.release();
            grabber = new OpenCVFrameGrabber(0);
            grabber.setImageWidth(200);
            grabber.setImageHeight(200);
            grabber.start();
            grabbedImage = grabberConverter.convert(grabber.grab());
        }
        grayImage  = IplImage.create(grabbedImage.width(),   grabbedImage.height(),   IPL_DEPTH_8U, 1);
        smallImage = IplImage.create(grabbedImage.width()/2, grabbedImage.height()/2, IPL_DEPTH_8U, 1);
        stop = false;
        } catch (Exception e) {
            if (exception == null) {
                exception = e;
                //repaint();
            }
        }
    }
    public void run() {
    	try{
            while (!stop && (grabbedImage = grabberConverter.convert(grabber.grab())) != null) {
                if (faces == null) {
                    cvClearMemStorage(storage);
                    cvCvtColor(grabbedImage, grayImage, CV_BGR2GRAY);
                    cvResize(grayImage, smallImage, CV_INTER_AREA);
                    long time1=System.currentTimeMillis();
                    //faces = cvHaarDetectObjects(grayImage, classifier, storage,
                    //        1.1, 3, CV_HAAR_DO_CANNY_PRUNING);
                    int angle=0;
                    int num=0;
                    while((num==0)&&(angle<=30)){
                    	rImage=rotateImage(grayImage,angle,clock);
                    faces = cvHaarDetectObjects(rImage, classifier, storage, 1.1, 3,
                    		CV_HAAR_DO_CANNY_PRUNING);
                    angle+=3;
                    num=faces.total();
                    }
                    /*faces = cvHaarDetectObjects(rotateImage(smallImage,angle,true), classifier, storage, 1.1, 3,
                    		CV_HAAR_DO_CANNY_PRUNING);
                    num=faces.total();*/
                    long time2=System.currentTimeMillis();
                  	System.out.println(time2-time1);
                    if (num>0) 
                    	{
                    	
                    	angle=angle-3;
                    	CvRect r = new CvRect(cvGetSeqElem(faces, 1));
                    	//计算参考点位置，并通过atan求得角度
                    	if (!clock){
                    		x_ori=Math.cos(angle*CV_PI/180)*58+Math.sin(angle*CV_PI/180)*smallImage.height();
                    		y_ori=rImage.height()-58*Math.sin(angle*CV_PI/180);
                    		Angle=180*Math.atan2(y_ori-(r.y()+1/2*r.height()),r.x()+1/2*r.width()-x_ori)/CV_PI-angle;
                    	}
                    	else 
                    	{
                    		x_ori=Math.cos(angle*CV_PI/180)*58;
                    		//y_ori=rImage.height()-(grabbedImage.width()-23)*Math.sin(angle*CV_PI/180);
                    		y_ori=grabbedImage.height()*Math.cos(angle*CV_PI/180)+Math.sin(angle*CV_PI/180)*23;
                    		double temp=180*Math.atan2(y_ori-(r.y()+1/2*r.height()),r.x()+1/2*r.width()-x_ori)/CV_PI-angle;
                    		//if (temp>=90)
                    		Angle=temp;
                    	}
                    	System.out.println(Angle);
                    	System.out.println(x_ori+" "+y_ori);
                    	System.out.println((r.x()+1/2*r.width())+" "+(r.y()+1/2*r.height()));
                    	System.out.println(rImage.width()+" "+rImage.height());
                    	System.out.println(smallImage.width()+" "+smallImage.height());
                    	//Angle=180*Math.atan2(y_ori-(r.y()+1/2*r.height()),r.x()+1/2*r.width()-x_ori)/CV_PI-angle;
                    	//}
                    	}
                    //repaint();
                    faces=null;
                }
            }
            grabbedImage = grayImage=smallImage= null;
            grabber.stop();
            grabber.release();
            grabber = null;
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
    }
    //完成图像旋转
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
        //计算为了完成旋转，至少需要多大的新图像
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
      //设置仿射变换矩阵
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
        //进行图像裁切
        cvGetQuadrangleSubPix(temp, dst, M);  
        cvReleaseImage(temp);  
        return dst;  
    }
    public double getAngle(){return Angle;}
    public void ClockChange(){
    	clock=!clock;
    }
    public void stop() {
        stop = true;
    }
}
