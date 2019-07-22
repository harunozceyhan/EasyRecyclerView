package tr.com.ozcapps.easyrecyclerview.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class ViewData(val viewId: String)