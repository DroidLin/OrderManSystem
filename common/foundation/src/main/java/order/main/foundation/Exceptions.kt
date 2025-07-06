package order.main.foundation

class BizException : RuntimeException {

    val code: Int

    constructor(code: Int) : super() {
        this.code = code
    }

    constructor(message: String?, code: Int) : super(message) {
        this.code = code
    }

    constructor(message: String?, cause: Throwable?, code: Int) : super(message, cause) {
        this.code = code
    }

    constructor(
        message: String?,
        cause: Throwable?,
        enableSuppression: Boolean,
        writableStackTrace: Boolean,
        code: Int
    ) : super(message, cause, enableSuppression, writableStackTrace) {
        this.code = code
    }

    constructor(cause: Throwable?, code: Int) : super(cause) {
        this.code = code
    }
}