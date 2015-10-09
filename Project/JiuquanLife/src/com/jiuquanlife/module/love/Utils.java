package com.jiuquanlife.module.love;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class Utils {
	public static String getDistance(String distance) {
		if (TextUtils.isEmpty(distance)) {
			return "0";
		}

		int distanceInt = 0;
		float value = 0;
		String uint = "m";
		try {
			distanceInt = Integer.valueOf(distance);
			if (distanceInt >= 1000) {
				value = distanceInt / 1000;
				uint = "km";
			} else {
				value = distanceInt;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return value + uint;
	}

	public static void getPermisson(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			// �õ��Լ��İ���
			String pkgName = pi.packageName;
			StringBuilder tv = new StringBuilder();
			PackageInfo pkgInfo = pm.getPackageInfo(pkgName,
					PackageManager.GET_PERMISSIONS);// ͨ�����������ذ���Ϣ
			String sharedPkgList[] = pkgInfo.requestedPermissions;// �õ�Ȩ���б�

			for (int i = 0; i < sharedPkgList.length; i++) {
				String permName = sharedPkgList[i];

				PermissionInfo tmpPermInfo = pm.getPermissionInfo(permName, 0);// ͨ��permName�õ���Ȩ�޵���ϸ��Ϣ
				PermissionGroupInfo pgi = pm.getPermissionGroupInfo(
						tmpPermInfo.group, 0);// Ȩ�޷�Ϊ��ͬ��Ⱥ�飬ͨ��Ȩ���������ǵõ���Ȩ������ʲô���͵�Ȩ�ޡ�

				tv.append(i + "-" + permName + "\n");
				tv.append(i + "-" + pgi.loadLabel(pm).toString() + "\n");
				tv.append(i + "-" + tmpPermInfo.loadLabel(pm).toString() + "\n");
				tv.append(i + "-" + tmpPermInfo.loadDescription(pm).toString()
						+ "\n");
				tv.append("Device" + "\n");

			}

			System.out.println("Perm:" + tv.toString());
		} catch (NameNotFoundException e) {
			Log.e("##ddd", "Could'nt retrieve permissions for package");

		}
	}

	public static String encodeContent(String content) {

		return Uri.encode(content);

	}
}
