# Приложение WebView

Это простое Android-приложение с использованием WebView, которое загружает веб-страницу.

## Особенности

- Загрузка веб-страницы через WebView.
- Включена поддержка JavaScript.
- Пользовательский WebViewClient для обработки ссылок внутри приложения.

## Установка

1. Откройте проект в Android Studio.
2. Соберите и запустите приложение на устройстве Android или эмуляторе.

## Основные моменты кода

### Инициализация WebView с поддержкой JavaScript:
```java
WebView mWebView = findViewById(R.id.activity_main_webview);
WebSettings webSettings = mWebView.getSettings();
webSettings.setJavaScriptEnabled(true);
```

### Пользовательский WebViewClient для обработки ссылок:
```java
mWebView.setWebViewClient(new WebViewClient() {
@Override
public boolean shouldOverrideUrlLoading(WebView view, String url) {
view.loadUrl(url);
return true;
}
});
```

### URL по умолчанию:
```java
mWebView.loadUrl("http://192.168.103.222");
```
