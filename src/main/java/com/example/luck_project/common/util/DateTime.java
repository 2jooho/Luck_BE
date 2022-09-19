package com.example.luck_project.common.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateTime {

    /**
     * 날짜 포맷 형식 변환
     * @param date
     * @param pattern
     * @return
     */
    public static String getFormatString (Date date, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern, java.util.Locale.KOREA);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 현재 시간정보 반환 						<br/>
     * caseCode								<br/>
     * 		1: yyyy-MM-dd HH:mm:ss			<br/>
     * 		2: yyyy-MM-dd HH:mm				<br/>
     * 		3: yyyy-MM-dd HH				<br/>
     * 		4: yyyy-MM-dd					<br/>
     * 		5: yyyy-MM						<br/>
     * 		6: yyyy							<br/>
     * 		7: HH:mm:ss						<br/>
     * 		8: HH:mm						<br/>
     * 		9: HH							<br/>
     * 	 	10: MM/dd						<br/>
     *      11: yyyyMMddHH:mm:ss            <br/>
     *      12: yyyy년 MM월 dd일           	<br/>
     *      13: yyyyMMddHHmmss              <br/>
     *      14: yyyyMMdd		            <br/>
     *      15: HHmmss			            <br/>
     *      16: HH:mmssyyddMM				<br/>
     * 		default: yyyy-MM-dd HH:mm:ss	<br/>
     * @param caseCode
     * @return
     */
    public static String getCurrentDate(int caseCode){
        String format = null;

        switch(caseCode){
            case 14:
                format = "yyyyMMdd";
                break;
            case 15:
                format = "HHmmss";
                break;
            case 16:
                format = "HH:mmssyyddMM";
                break;
            default:
                format = "yyyy-MM-dd HH:mm:ss";
                break;
        }

        FastDateFormat fdf = FastDateFormat.getInstance(format, Locale.KOREA);
        return fdf.format(new Date());
    }
}