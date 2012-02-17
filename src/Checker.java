import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author josh the actual task that checks the raspberrypi site
 * 
 */

public class Checker {
	// frequency to check the site
	int minutes = 5;
	private final Timer timer = new Timer();
	private JLabel labelLastChecked;
	private JLabel labelRaspberryPiStatus;
	private String htmlContent;

	public Checker(JLabel labelLastChecked, JLabel labelRaspberryPiStatus) {
		this.labelLastChecked = labelLastChecked;
		this.labelRaspberryPiStatus = labelRaspberryPiStatus;
	}

	private boolean hasRaspBerryPi() {
		this.htmlContent = getSiteContent();
		if (htmlContent != null) {
			Pattern myPattern = Pattern.compile("(model a|model b|\\$25|\\$35)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = myPattern.matcher(this.htmlContent);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	private String getSiteContent() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://raspberrypi.com");
		try {
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				entity = new BufferedHttpEntity(entity);
				return (EntityUtils.toString(entity));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public void start() {
		timer.schedule(new TimerTask() {
			public void run() {
				checkRaspberryPiIsReleased();
			}
		}, new Date(), minutes * 1000 * 60);
	}

	public void checkRaspberryPiIsReleased() {
		Date lastCheckedDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
		String status = "last checked: " + sdf.format(lastCheckedDate);
		labelLastChecked.setText(status.toUpperCase());
		if (hasRaspBerryPi()) {
			labelRaspberryPiStatus.setForeground(new Color(14, 121, 16));
			labelRaspberryPiStatus.setText("YEP!");
			System.out.println("check hasRaspberryPi? YES!!, at "
					+ sdf.format(lastCheckedDate));
		} else {
			labelRaspberryPiStatus.setForeground(new Color(99, 99, 99));
			labelRaspberryPiStatus.setText("NOPE.");
			System.out.println("check hasRaspberryPi? NOPE., at "
					+ sdf.format(lastCheckedDate));
		}
	}
}
