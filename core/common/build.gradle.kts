plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    // api: DispatcherProvider's public signature exposes CoroutineDispatcher,
    // so consumers need it on their compile classpath too.
    api(libs.coroutines.core)
    testImplementation(libs.junit4)
}
