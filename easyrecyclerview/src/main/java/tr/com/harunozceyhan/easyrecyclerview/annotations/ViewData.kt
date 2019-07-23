package tr.com.harunozceyhan.easyrecyclerview.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class ViewData(val viewId: String)