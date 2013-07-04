package com.hzth.myapp.html;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hzth.myapp.core.util.HttpURLConnectionUtil;

public class HomeWorker {

	public static void main(String[] args) throws Exception {
		int paperNum = 575;
		Map<String, String> answerMap = readAnswer2(paperNum);
		setAnswer2(answerMap, paperNum);
	}

	private static void setAnswer2(Map<String, String> answerMap, int paperNum) throws Exception {
		String url = "http://www.ibucm.com/ibucm2012/student/StudentHomeworkOpen.aspx?Id=" + paperNum;
		Map<String, String> cookieMap = new HashMap<String, String>();
		cookieMap.put("ASP.NET_SessionId", "4wh04o55tsx4cx45parvxluf");
		cookieMap.put(".ASPXAUTH", "11FB00E05DE363AB87219159F9905026BCE75D5E9EA587DF3B564FDCFB47F83FA1A76331AD370D727F2DB6CE13DBE658F3EC1EF63209ED0723D158055F9AE5E4642729B74FC6D76FA0EE315CA8FE712C78953EBBBB883429A08B24A7304AD4284911447DB4EF522EDCA0B4431CEF87A0FD01A14067862F2167B59DC342D5FC68C4AF7BB10DCF5BB9E343D77F4F4E06868B52E1DB163D13FD60EF211708136043BD0E697AA2756B8F70E909F7B67B7F45380EE1B545F00C254494F5760AF92D813FC2D3F3DDF06EC3A85F2F05154FDCCDDB7E70689CE7605602C8AA9F9D87EDD17205E53BACD5F2FA5CFA061F6EBF0FA95588A8D5EA05AD4904D336E49243B656C665013CB0F3422D65D157C80EA6870935C2B47FBBC765B85F98B33299F6EB5507B8479E31C039F959733C09A29B4FF3FF7879ED8B7D9EA78E03F4C28367EE1A58247CE33EE5A2EF1ED8039BCDDACBDC39BFE8A91B6757CCCF0B4DBA570C5C3548F70DFC22504BD59F01E9DA3BA58E9C2B8944994330B1C5D89F48007C3B8363E60A10B17275ED6F51DFF9DB842F05E398AA15C13AB894AB6BCB523BEB28F436DD9369B6143B2E736A485135234A72CA336B55ECC26551855E95C20673B3AF28F980EBB30AEE005DA0DE79F59F2907E92F58F128E80C67958F7C1A940435D82824DE04B5AA56E853F802BF2B09910D3841922F3A7B5488D13A2375D772031CD076C99303C89F1533C401C6150A76FF76C4C5BE91914B71BFD8FA56ECF4569E00F6B775E83AAA6109A399CC123FC8C350AD453EA5");
		String result = HttpURLConnectionUtil.getResponse(url, null, cookieMap, false);
		Document document = Jsoup.parse(result);
		int i = 0;
		Elements spans = document.select("span[id$=_lbSubjectText]");
		for (Element span : spans) {
			String answer = answerMap.get(span.text());
			if (StringUtils.isBlank(answer)) {
				i++;
				continue;
			}
			String key = span.id().replaceAll("_lbSubjectText", "");
			Element tab = document.getElementById(key + "_rblAnswer");
			Elements labels = tab.select("label");
			for (Element label : labels) {
				if (label.text().equals(answer)) {
					System.out.println("window.frames['mainFrame'].document.getElementById('" + label.attr("for") + "').click();");
					continue;
				}
			}
		}
		System.out.println(i + "道题没有答案");
	}

	private static Map<String, String> readAnswer2(int paperNum) throws Exception {
		String url = "http://www.ibucm.com/ibucm2012/student/StudentHomeworkLook.aspx?Id=" + paperNum;
		Map<String, String> cookieMap = new HashMap<String, String>();
		cookieMap.put("ASP.NET_SessionId", "xuddlh55jlfjouixa2g25miv");
		cookieMap.put(".ASPXAUTH", "DC5EE6973F7170EB7B6C6183D0CC60AC6C94F0526960D4350661F6BED6C1BAF76A3EDA37E3094DF0E0CFEA6E2DB4F12D44B2EBE1F5AEF7E4D98B5AC6DD22BD457526CEE452A2D2EC067E7A0576BF3A638AB4EA5E0DF049DE2C95EF58053668374416FD788B6A75D60F67901A961C3DF4783CCAF1751D3D340DA793A7616A34E022E988440878D1CA6AE471013B20B4EBA4580B9E2234B75039206344330E4C7173914D2016EB6A7CB1FFE56F5B85C4AA4494667E6A8E7CEA916117DD8DC8D6C89AAFB4C165A9746B64E29CAA330FCE923AD9BEEE8438B6E7E39A9749CA6A2A4D4300A3B4DC6CF1C6085C4CCCEC43A48105FBD0105C1D419904DAFA90E1A3C7D7504A6092C118B3EC734F95C4118BAC2F0952D8C46DD5235C60433885A41969CD877AF4F6A3880CACEEA028174189B02033A0091088B12D0499267D3ACBA055CC10246439A976ACE6357A549FD66F1D11333077EA80BCC4F65E111F8750D113132DE40C9BE8F4FC995F72362C994D6E69C127E301A086D513B61D5AB327510E138BDF33F5FC64B0CE9C54512C2AEB0BDFBF4984220092B2242F50170D5DDF3E2E2DC4DD5336FBBF9B4409585A300830C990E7953CD7ECE8C43AE3932A05E4D5E66DF6D907D97CD834C6B1C1A5072402FA24B14A044F5C9FD8230861F39491DE4C536BB75C9350A1701F7349ADEBDC10E54F03741D0F4F5790D684F8C7DDB66E3B6858EE7FB86E5A0BD3D2FAC2D176762532FB4CC51A47DDA9E36F62AE3258DF3712F860E0B7CD73EAF74B89ED9D6764E713E9AD8B");
		String result = HttpURLConnectionUtil.getResponse(url, null, cookieMap, false);
		Document document = Jsoup.parse(result);
		Map<String, String> answerMap = new HashMap<String, String>();
		Set<String> questionSet = new HashSet<String>();
		int i = 0;
		int j = 0;
		Elements checkedRadio = document.select("input[type=radio][checked=checked]");
		for (Element radio : checkedRadio) {
			String id = radio.id();
			String answer = document.select("label[for=" + id + "]").text();
			String key = id.substring(0, id.indexOf("_rblAnswer_"));
			Element span = document.getElementById(key + "_lbSubjectText");
			if (StringUtils.isNotBlank(document.getElementById(key + "_lbRightAnswer").text())) {
				j++;
				continue;
			}
			String question = span.text();
			if (questionSet.contains(question)) {
				i++;
				answerMap.remove(question);
				continue;
			}
			questionSet.add(question);
			answerMap.put(question, answer);
		}
		System.out.println(i + "道题问题重复");
		System.out.println(j + "道题错误");
		return answerMap;
	}

}
