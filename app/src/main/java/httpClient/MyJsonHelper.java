package httpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MyJsonHelper {
	public static Map<String, String> parseJsonToMap(String jsonString,
													 String methodName) {
		if (methodName.equals("parseLoginJson")) {
			return parseLoginJson(jsonString);
		} else if (methodName.equals("parseLastBlogJsonToMap")) {
			return parseLastBlogJsonToMap(jsonString);
		} else if (methodName.equals("parseLastNewsJsonToMap")) {
			return parseLastNewsJsonToMap(jsonString);
		} else if (methodName.equals("parseNewsDetailJsonToMap")) {
			return parseNewsDetailJsonToMap(jsonString);
		} else if (methodName.equals("parseLastNewsCommentJsonToMap")) {
			return parseLastNewsCommentJsonToMap(jsonString);
		} else if (methodName.equals("parseAddCommentJsonToMap")) {
			return parseAddCommentJsonToMap(jsonString);
		}
		return null;
	}

	public static List<Map<String, String>> parseJsonToList(String jsonString,
															String methodName) {
		if (methodName.equals("parseBlogJsonToList")) {
			return parseBlogJsonToList(jsonString);
		} else if (methodName.equals("parseNewsJsonToList")) {
			return parseNewsJsonToList(jsonString);
		} else if (methodName.equals("parseCommentsJsonToList")) {
			return parseCommentsJsonToList(jsonString);
		} else if (methodName.equals("parseCourseJsonToList")) {
			return parseCourseJsonToList(jsonString);
		} else if (methodName.equals("parseSubCourseJsonToList")) {
			return parseSubCourseJsonToList(jsonString);
		} else if (methodName.equals("parseProductJsonToList")) {
			return parseProductJsonToList(jsonString);
		} else if (methodName.equals("parseSubProductJsonToList")) {
			return parseSubProductJsonToList(jsonString);
		} else if (methodName.equals("parseExamRecentJsonToList")) {
			return parseExamRecentJsonToList(jsonString);
		} else if (methodName.equals("parseExamHistoryJsonToList")) {
			return parseExamHistoryJsonToList(jsonString);
		}
		return null;
	}

	// 解析“登录信息”的json
	public static Map<String, String> parseLoginJson(String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject object = new JSONObject(jsonString);
			map.put("status", object.getString("status"));
			JSONObject object_user = object.getJSONObject("user");
			map.put("truename", object_user.getString("name"));
			map.put("username", object_user.getString("nickname"));
			map.put("userid", object_user.getString("id"));
			map.put("email", object_user.getString("emailAddress"));
			map.put("company", object_user.getString("remark"));
			map.put("department", object_user.getString("reserve"));
			map.put("telephone", object_user.getString("telephone"));
			map.put("position", object_user.getString("job"));
			map.put("userType", object_user.getString("userType"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

	// 解析最新一条"经理发布"的json
	public static Map<String, String> parseLastBlogJsonToMap(String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject object = new JSONObject(jsonString);
			map.put("title", object.getString("title"));
			map.put("content", object.getString("content"));
			map.put("releaseTime", object.getString("releaseTime"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

	// 解析"经理博客信息"的json
	public static List<Map<String, String>> parseBlogJsonToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONArray array_data = new JSONArray(jsonString);
			for (int i = 0; i < array_data.length(); i++) {
				JSONObject object_data = array_data.getJSONObject(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", object_data.getString("id"));
				map.put("title", object_data.getString("title"));
				map.put("content", object_data.getString("content"));
				map.put("releaseTime", object_data.getString("releaseTime"));
				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析"经理博客信息"的json
	public static boolean parseBlogDeleteJson(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			if (object.getString("success").equals("true")) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 解析"最新一条新闻"的json
	public static Map<String, String> parseLastNewsJsonToMap(String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("Rows");
			for (int i = 0; i < 1; i++) {
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("description", object_data.getString("description"));
				map.put("releasedate", object_data.getString("releasedate"));
				map.put("simage", object_data.getString("simage"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 解析"新闻信息列表”的json
	public static List<Map<String, String>> parseNewsJsonToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("Rows");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("id", object_data.getString("id"));
				map.put("title", object_data.getString("title"));
				map.put("smalltitle", object_data.getString("smalltitle"));
				// map.put("description", object_data.getString("description"));
				map.put("simage", object_data.getString("simage"));
				map.put("releasedate", object_data.getString("releasedate"));

				String uploadType = object_data.getString("uploadType");
				String[] arrUploadType = uploadType.split(";");
				map.put("uploadType", arrUploadType[0]);
				map.put("mvurl", object_data.optString("url"));

				// String url = object_data.optString("url");
				// if (url.indexOf(";") > 0) {
				// String[] arrUrl = url.split(";");
				// map.put("mvurl", arrUrl[0]);
				// } else {
				// map.put("mvurl", url);
				// }
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析"单条新闻"的json
	public static Map<String, String> parseNewsDetailJsonToMap(String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject object = new JSONObject(jsonString);
			map.put("description", object.getString("description"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

	// 解析新闻的最后一条评论的json
	public static Map<String, String> parseLastNewsCommentJsonToMap(
			String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("Rows");
			for (int i = 0; i < array_data.length(); i++) {
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("description", object_data.getString("description"));
				map.put("id", object_data.getString("id"));
				map.put("createdate", object_data.getString("createdate"));
				JSONArray array_regusers = object_data.getJSONArray("reguser");
				JSONObject object_reguser = array_regusers.getJSONObject(0);
				map.put("username", object_reguser.getString("username"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

	// 解析"评论列表”的json
	public static List<Map<String, String>> parseCommentsJsonToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("Rows");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("createdate", object_data.getString("createdate"));
				map.put("description", object_data.getString("description"));

				JSONObject object_reguser = object_data
						.getJSONObject("reguser");
				map.put("username", object_reguser.getString("username"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析发表评论的返回json信息
	public static Map<String, String> parseAddCommentJsonToMap(String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject object = new JSONObject(jsonString);
			map.put("success", object.getString("success"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

	// 解析"公开课”的一级json
	public static List<Map<String, String>> parseCourseJsonToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("Rows");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("id", object_data.getString("id"));
				map.put("title", object_data.getString("title"));
				map.put("releasedate", object_data.getString("releasedate"));
				map.put("simage", object_data.getString("simage"));
				map.put("taskid", object_data.getString("taskid"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析"公开课”的二级json
	public static List<Map<String, String>> parseSubCourseJsonToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("Rows");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("id", object_data.getString("id"));
				map.put("title", object_data.getString("title"));
				map.put("smalltitle", object_data.getString("smalltitle"));
				// map.put("description", object_data.getString("description"));
				map.put("simage", object_data.getString("simage"));
				map.put("releasedate", object_data.getString("releasedate"));
				map.put("uploadType", object_data.getString("uploadType"));
				map.put("mvurl", object_data.optString("mvurl"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析"产品速查”的一级json
	public static List<Map<String, String>> parseProductJsonToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("Rows");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("id", object_data.getString("id"));
				map.put("title", object_data.getString("title"));
				map.put("releasedate", object_data.getString("releasedate"));
				map.put("taskid", object_data.getString("taskid"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析"产品速查”的二级json
	public static List<Map<String, String>> parseSubProductJsonToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("Rows");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("id", object_data.getString("id"));
				map.put("title", object_data.getString("title"));
				map.put("releasedate", object_data.getString("releasedate"));
				map.put("mvurl", object_data.optString("mvurl"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析"考试须知”的json
	public static Map<String, String> parseExamNoticeJsonToMap(String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONObject object_data = object.getJSONObject("data");
			map.put("content", object_data.getString("examvo.examInstructions"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 解析"考试记录”的json
	public static List<Map<String, String>> parseExamHistoryJsonToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("items");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("id", object_data.getString("id"));
				map.put("batchNo", object_data.getString("batchNo"));
				map.put("examTitle", object_data.getString("examTitle"));
				map.put("examstartDate", object_data.getString("examstartDate"));
				map.put("examendDate", object_data.getString("examendDate"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析"近期考试安排”的json
	public static List<Map<String, String>> parseExamRecentJsonToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("items");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				if (Integer.parseInt(object_data.getString("completeFlag")) >= 0) {
					map.put("id", object_data.getString("id"));
					map.put("batchNo", object_data.getString("batchNo"));
					map.put("examTitle", object_data.getString("examTitle"));
					map.put("examstartDate",
							object_data.getString("examstartDate"));
					map.put("examendDate", object_data.getString("examendDate"));
					map.put("completeFlag",
							object_data.getString("completeFlag"));
					list.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析"近期考试安排”的json，只获取考试日期
	public static HashSet<String> parseRecentExamDateJsonToList(
			String jsonString) {
		HashSet<String> set = new HashSet<String>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("items");
			for (int i = 0; i < array_data.length(); i++) {
				JSONObject object_data = array_data.getJSONObject(i);
				HashSet<String> data = getDatesInfoBetweenTwoDates(
						object_data.getString("examstartDate"),
						object_data.getString("examendDate"));
				set.addAll(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return set;
	}

	// 解析"近期考试安排”中最近一次的考试间距日期
	public static Map<String, String> parseLastestExamJsonToMap(
			String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("items");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				if (Integer.parseInt(object_data.getString("completeFlag")) > 0) {
					map.put("completeFlag",
							object_data.getString("completeFlag"));
					return map;
				}
				if (Integer.parseInt(object_data.getString("completeFlag")) == 0) {
					map.put("completeFlag",
							object_data.getString("completeFlag"));
					map.put("examendDate", object_data.getString("examendDate"));
					map.put("examId", object_data.getString("id"));
					map.put("paperNo", object_data.getString("paperRef"));
					map.put("batchNo", object_data.getString("batchNo"));
					return map;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 解析"考试试卷”的json
	public static List<Map<String, String>> parseExamPaperJsonToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("items");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("questionsType", object_data.getString("questionsType"));
				map.put("questionsStems",
						object_data.getString("questionsStems"));
				map.put("questions", object_data.getString("questions"));
				map.put("questionsResult",
						object_data.getString("questionsResult"));
				map.put("scores", object_data.getString("scores"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析"考试试卷”的json
//	public static List<ExamBean> parseExamPaperJsonToListBean(String jsonString) {
//		List<ExamBean> list = new ArrayList<ExamBean>();
//		try {
//			JSONObject object = new JSONObject(jsonString);
//			JSONArray array_data = object.getJSONArray("items");
//			for (int i = 0; i < array_data.length(); i++) {
//				ExamBean examBean = new ExamBean();
//				JSONObject object_data = array_data.getJSONObject(i);
//				examBean.setId(object_data.getInt("id"));
//				examBean.setQuestionsType(object_data
//						.getString("questionsType"));
//				examBean.setQuestionsStems(object_data
//						.getString("questionsStems"));
//				examBean.setQuestions(object_data.getString("questions"));
//				examBean.setQuestionsResult(object_data
//						.getString("questionsResult"));
//				examBean.setScores(object_data.getString("scores"));
//				list.add(examBean);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

	// 解析"提交试卷”的返回json
	public static Map<String, String> parseSubmitExamJsonToMap(String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject object = new JSONObject(jsonString);
			map.put("result", object.getString("success"));
			map.put("mess", object.getString("mess"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 解析"所有试卷”的信息
	public static List<Map<String, String>> parseExamPaperInfoToList(
			String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("items");
			for (int i = 0; i < array_data.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object_data = array_data.getJSONObject(i);
				map.put("paperTitle", object_data.getString("paperTitle"));
				map.put("paperNo", object_data.getString("paperNo"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 解析"当日考试的试卷”的信息
	public static Map<String, String> parseCurrentExamPaperInfoToMap(
			String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject object = new JSONObject(jsonString);
			JSONArray array_data = object.getJSONArray("items");
			JSONObject object_data = array_data.getJSONObject(0);
			map.put("paperTitle", object_data.getString("paperTitle"));
			map.put("paperNo", object_data.getString("paperNo"));
			map.put("batchNo", object_data.getString("batchNo"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 解析"添加新博客信息"的json
	public static boolean parseAddBlog(String jsonString) {
		try {
			JSONObject object = new JSONObject(jsonString);
			if (object.getString("success").equals("true")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static HashSet<String> getDatesInfoBetweenTwoDates(String date1,
															  String date2) {
		HashSet<String> arrSignalDate = new HashSet<String>();
		try {
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = df.parse(date1);
			startCalendar.setTime(startDate);
			Date endDate = df.parse(date2);
			endCalendar.setTime(endDate);
			arrSignalDate.add(date1);
			arrSignalDate.add(date2);
			while (true) {
				startCalendar.add(Calendar.DAY_OF_MONTH, 1);
				if (startCalendar.getTimeInMillis() < endCalendar
						.getTimeInMillis()) {
					arrSignalDate.add(df.format(startCalendar.getTime()));
				} else {
					break;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return arrSignalDate;
	}
}
