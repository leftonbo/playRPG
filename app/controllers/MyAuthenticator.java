package controllers;

import play.cache.Cache;
import play.mvc.*;

public class MyAuthenticator extends Security.Authenticator {
	Application a;

	private static final String appKeyString = "playRPG-d2r";
	
	/** 未認証状態でアクセスされたときのアクション */
	@Override
	public Result onUnauthorized(Http.Context arg0)
	{
	    // ログインページへジャンプします
		Application.flash("danger", "おきのどくですが、あなたはログアウトされました。");
	    return redirect(controllers.routes.Application.index());
	}
	
	@Override
	public String getUsername(Http.Context context)
	{
	    final Http.Cookie userCookie = context.request().cookie(appKeyString);
	    if (userCookie == null) return null;    // ログインクッキーなし
	
	    final String userToken = userCookie.value();
	    final Object userInfo = Cache.get(userToken + ".userInfo");
	    if (!(userInfo instanceof String)){
	        context.response().discardCookie(appKeyString); // すでにキャッシュにないのでクッキーも破棄
	        return null;
	    }
	
	    // アクセスのたびにログイン情報登録をリフレッシュする
	    registerLoginSession(context, userToken, userInfo);
	    
	    return (String)userInfo;
	}
	
	public static void registerLoginSession(Http.Context context, String userToken, Object userInfo)
	{
	    // アプリケーションキャッシュの有効期限を今から30分後に
	    Cache.set(userToken + ".userInfo", userInfo, 60 * 30);
	    // ログインクッキーの有効期限を今から7日後に
	    context.response().setCookie(appKeyString, userToken, 60*60*24*7);
	}
	
	public static void unregisterLoginSession(Http.Context context)
	{
	    final Http.Cookie userCookie = context.request().cookie(appKeyString);
	    if (userCookie == null) return;
	    // アプリケーションキャッシュからログイン状態を削除する
	    Cache.remove(userCookie.value() + ".userInfo");
	    // ログインクッキーを削除させる
	    context.response().discardCookie(appKeyString);
	}

}
