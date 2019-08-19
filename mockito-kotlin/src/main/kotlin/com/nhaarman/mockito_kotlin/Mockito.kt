/*
 * The MIT License
 *
 * Copyright (c) 2016 Niek Haarman
 * Copyright (c) 2007 Mockito contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.nhaarman.mockito_kotlin

import com.nhaarman.mockito_kotlin.createinstance.createInstance
import org.mockito.*
import org.mockito.invocation.InvocationOnMock
import org.mockito.listeners.InvocationListener
import org.mockito.mock.SerializableMode
import org.mockito.stubbing.Answer
import org.mockito.stubbing.OngoingStubbing
import org.mockito.stubbing.Stubber
import org.mockito.verification.VerificationMode
import org.mockito.verification.VerificationWithTimeout
import kotlin.DeprecationLevel.WARNING
import kotlin.reflect.KClass

fun after(millis: Long) = Mockito.after(millis)

/** Matches any object, excluding nulls. */
@Deprecated("V2 Migration", replaceWith = ReplaceWith("any", "com.nhaarman.mockitokotlin2.any"))
inline fun <reified T : Any> any() = Mockito.any(T::class.java) ?: createInstance<T>()

/** Matches anything, including nulls. */
@Deprecated("V2 Migration", replaceWith = ReplaceWith("anyOrNull", "com.nhaarman.mockitokotlin2.anyOrNull"))
inline fun <reified T : Any> anyOrNull(): T = Mockito.any<T>() ?: createInstance<T>()

/** Matches any vararg object, including nulls. */
@Deprecated("V2 Migration", replaceWith = ReplaceWith("anyVararg", "com.nhaarman.mockitokotlin2.anyVararg"))
inline fun <reified T : Any> anyVararg(): T = Mockito.any<T>() ?: createInstance<T>()

/** Matches any array of type T. */
@Deprecated("V2 Migration", replaceWith = ReplaceWith("anyArray", "com.nhaarman.mockitokotlin2.anyArray"))
inline fun <reified T : Any?> anyArray(): Array<T> = Mockito.any(Array<T>::class.java) ?: arrayOf()

/**
 * Creates a custom argument matcher.
 * `null` values will never evaluate to `true`.
 *
 * @param predicate An extension function on [T] that returns `true` when a [T] matches the predicate.
 */
@Deprecated("V2 Migration", replaceWith = ReplaceWith("argThat", "com.nhaarman.mockitokotlin2.argThat"))
inline fun <reified T : Any> argThat(noinline predicate: T.() -> Boolean) = Mockito.argThat<T?> { arg -> arg?.predicate() ?: false } ?: createInstance(T::class)

/**
 * Creates a custom argument matcher.
 * `null` values will never evaluate to `true`.
 *
 * @param predicate An extension function on [T] that returns `true` when a [T] matches the predicate.
 */
@Deprecated("V2 Migration", replaceWith = ReplaceWith("argForWhich", "com.nhaarman.mockitokotlin2.argForWhich"))
inline fun <reified T : Any> argForWhich(noinline predicate: T.() -> Boolean) = argThat(predicate)

/**
 * Creates a custom argument matcher.
 * `null` values will never evaluate to `true`.
 *
 * @param predicate A function that returns `true` when given [T] matches the predicate.
 */
@Deprecated("V2 Migration", replaceWith = ReplaceWith("argWhere", "com.nhaarman.mockitokotlin2.argWhere"))
inline fun <reified T : Any> argWhere(noinline predicate: (T) -> Boolean) = argThat(predicate)

/**
 * For usage with verification only.
 *
 * For example:
 *  verify(myObject).doSomething(check { assertThat(it, is("Test")) })
 *
 * @param predicate A function that performs actions to verify an argument [T].
 */
@Deprecated("V2 Migration", replaceWith = ReplaceWith("check", "com.nhaarman.mockitokotlin2.check"))
inline fun <reified T : Any> check(noinline predicate: (T) -> Unit) = Mockito.argThat<T?> { arg ->
    if (arg == null) error("""The argument passed to the predicate was null.

If you are trying to verify an argument to be null, use `isNull()`.
If you are using `check` as part of a stubbing, use `argThat` or `argForWhich` instead.
""".trimIndent())
    predicate(arg)
    true
} ?: createInstance(T::class)

@Deprecated("V2 Migration", replaceWith = ReplaceWith("atLeast", "com.nhaarman.mockitokotlin2.atLeast"))
fun atLeast(numInvocations: Int): VerificationMode = Mockito.atLeast(numInvocations)!!

@Deprecated("V2 Migration", replaceWith = ReplaceWith("atLeastOnce", "com.nhaarman.mockitokotlin2.atLeastOnce"))
fun atLeastOnce(): VerificationMode = Mockito.atLeastOnce()!!

@Deprecated("V2 Migration", replaceWith = ReplaceWith("atMost", "com.nhaarman.mockitokotlin2.atMost"))
fun atMost(maxNumberOfInvocations: Int): VerificationMode = Mockito.atMost(maxNumberOfInvocations)!!

@Deprecated("V2 Migration", replaceWith = ReplaceWith("calls", "com.nhaarman.mockitokotlin2.calls"))
fun calls(wantedNumberOfInvocations: Int): VerificationMode = Mockito.calls(wantedNumberOfInvocations)!!

@Deprecated("V2 Migration", replaceWith = ReplaceWith("clearInvocations", "com.nhaarman.mockitokotlin2.clearInvocations"))
fun <T> clearInvocations(vararg mocks: T) = Mockito.clearInvocations(*mocks)
@Deprecated("V2 Migration", replaceWith = ReplaceWith("description", "com.nhaarman.mockitokotlin2.description"))
fun description(description: String): VerificationMode = Mockito.description(description)

@Deprecated("V2 Migration", replaceWith = ReplaceWith("doAnswer", "com.nhaarman.mockitokotlin2.doAnswer"))
fun <T> doAnswer(answer: (InvocationOnMock) -> T?): Stubber = Mockito.doAnswer { answer(it) }!!

@Deprecated("V2 Migration", replaceWith = ReplaceWith("doCallRealMethod", "com.nhaarman.mockitokotlin2.doCallRealMethod"))
fun doCallRealMethod(): Stubber = Mockito.doCallRealMethod()!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("doNothing", "com.nhaarman.mockitokotlin2.doNothing"))
fun doNothing(): Stubber = Mockito.doNothing()!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("doReturn", "com.nhaarman.mockitokotlin2.doReturn"))
fun doReturn(value: Any?): Stubber = Mockito.doReturn(value)!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("doReturn", "com.nhaarman.mockitokotlin2.doReturn"))
fun doReturn(toBeReturned: Any?, vararg toBeReturnedNext: Any?): Stubber = Mockito.doReturn(toBeReturned, *toBeReturnedNext)!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("doThrow", "com.nhaarman.mockitokotlin2.doThrow"))
fun doThrow(toBeThrown: KClass<out Throwable>): Stubber = Mockito.doThrow(toBeThrown.java)!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("doThrow", "com.nhaarman.mockitokotlin2.doThrow"))
fun doThrow(vararg toBeThrown: Throwable): Stubber = Mockito.doThrow(*toBeThrown)!!

@Deprecated("V2 Migration", replaceWith = ReplaceWith("eq", "com.nhaarman.mockitokotlin2.eq"))
fun <T> eq(value: T): T = Mockito.eq(value) ?: value
@Deprecated("V2 Migration", replaceWith = ReplaceWith("ignoreStubs", "com.nhaarman.mockitokotlin2.ignoreStubs"))
fun ignoreStubs(vararg mocks: Any): Array<out Any> = Mockito.ignoreStubs(*mocks)!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("inOrder", "com.nhaarman.mockitokotlin2.inOrder"))
fun inOrder(vararg mocks: Any): InOrder = Mockito.inOrder(*mocks)!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("inOrder", "com.nhaarman.mockitokotlin2.inOrder"))
fun inOrder(vararg mocks: Any, evaluation: InOrder.() -> Unit) = Mockito.inOrder(*mocks).evaluation()

@Deprecated("V2 Migration", replaceWith = ReplaceWith("inOrder", "com.nhaarman.mockitokotlin2.inOrder"))
inline fun <T> T.inOrder(block: InOrderOnType<T>.() -> Any) {
    block.invoke(InOrderOnType(this))
}

class InOrderOnType<T>(private val t: T) : InOrder by inOrder(t as Any) {
    fun verify() : T = verify(t)
}

@Deprecated("V2 Migration", replaceWith = ReplaceWith("isA", "com.nhaarman.mockitokotlin2.isA"))
inline fun <reified T : Any> isA(): T = Mockito.isA(T::class.java) ?: createInstance<T>()
@Deprecated("V2 Migration", replaceWith = ReplaceWith("isNotNull", "com.nhaarman.mockitokotlin2.isNotNull"))
fun <T : Any> isNotNull(): T? = Mockito.isNotNull()
@Deprecated("V2 Migration", replaceWith = ReplaceWith("isNull", "com.nhaarman.mockitokotlin2.isNull"))
fun <T : Any> isNull(): T? = Mockito.isNull()

@Deprecated("V2 Migration", replaceWith = ReplaceWith("mock", "com.nhaarman.mockitokotlin2.mock"))
inline fun <reified T : Any> mock(
      extraInterfaces: Array<KClass<out Any>>? = null,
      name: String? = null,
      spiedInstance: Any? = null,
      defaultAnswer: Answer<Any>? = null,
      serializable: Boolean = false,
      serializableMode: SerializableMode? = null,
      verboseLogging: Boolean = false,
      invocationListeners: Array<InvocationListener>? = null,
      stubOnly: Boolean = false,
      @Incubating useConstructor: Boolean = false,
      @Incubating outerInstance: Any? = null
): T = Mockito.mock(T::class.java, withSettings(
      extraInterfaces = extraInterfaces,
      name = name,
      spiedInstance = spiedInstance,
      defaultAnswer = defaultAnswer,
      serializable = serializable,
      serializableMode = serializableMode,
      verboseLogging = verboseLogging,
      invocationListeners = invocationListeners,
      stubOnly = stubOnly,
      useConstructor = useConstructor,
      outerInstance = outerInstance
))!!

@Deprecated("V2 Migration", replaceWith = ReplaceWith("mock", "com.nhaarman.mockitokotlin2.mock"))
inline fun <reified T : Any> mock(
      extraInterfaces: Array<KClass<out Any>>? = null,
      name: String? = null,
      spiedInstance: Any? = null,
      defaultAnswer: Answer<Any>? = null,
      serializable: Boolean = false,
      serializableMode: SerializableMode? = null,
      verboseLogging: Boolean = false,
      invocationListeners: Array<InvocationListener>? = null,
      stubOnly: Boolean = false,
      @Incubating useConstructor: Boolean = false,
      @Incubating outerInstance: Any? = null,
      stubbing: KStubbing<T>.(T) -> Unit
): T = Mockito.mock(T::class.java, withSettings(
      extraInterfaces = extraInterfaces,
      name = name,
      spiedInstance = spiedInstance,
      defaultAnswer = defaultAnswer,
      serializable = serializable,
      serializableMode = serializableMode,
      verboseLogging = verboseLogging,
      invocationListeners = invocationListeners,
      stubOnly = stubOnly,
      useConstructor = useConstructor,
      outerInstance = outerInstance
)).apply {
    KStubbing(this).stubbing(this)
}!!

@Deprecated("V2 Migration", replaceWith = ReplaceWith("anyOrNull", "com.nhaarman.mockitokotlin2.stub"))
inline fun <T : Any> T.stub(stubbing: KStubbing<T>.(T) -> Unit) = this.apply { KStubbing(this).stubbing(this) }

@Deprecated("Use mock() with optional arguments instead.", ReplaceWith("mock<T>(defaultAnswer = a)"), level = WARNING)
inline fun <reified T : Any> mock(a: Answer<Any>): T = mock(defaultAnswer = a)

@Deprecated("Use mock() with optional arguments instead.", ReplaceWith("mock<T>(name = s)"), level = WARNING)
inline fun <reified T : Any> mock(s: String): T = mock(name = s)

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("Use mock() with optional arguments instead.", level = WARNING)
inline fun <reified T : Any> mock(s: MockSettings): T = Mockito.mock(T::class.java, s)!!

class KStubbing<out T>(private val mock: T) {
    fun <R> on(methodCall: R) = Mockito.`when`(methodCall)

    fun <R : Any> onGeneric(methodCall: T.() -> R, c: KClass<R>): OngoingStubbing<R> {
        val r = try {
            mock.methodCall()
        } catch(e: NullPointerException) {
            // An NPE may be thrown by the Kotlin type system when the MockMethodInterceptor returns a
            // null value for a non-nullable generic type.
            // We catch this NPE to return a valid instance.
            // The Mockito state has already been modified at this point to reflect
            // the wanted changes.
            createInstance(c)
        }
        return Mockito.`when`(r)
    }

    inline fun <reified R : Any> onGeneric(noinline methodCall: T.() -> R): OngoingStubbing<R> {
        return onGeneric(methodCall, R::class)
    }

    fun <R> on(methodCall: T.() -> R): OngoingStubbing<R> {
        return try {
            Mockito.`when`(mock.methodCall())
        } catch(e: NullPointerException) {
            throw MockitoKotlinException("NullPointerException thrown when stubbing. If you are trying to stub a generic method, try `onGeneric` instead.", e)
        }
    }
}

infix fun <T> OngoingStubbing<T>.doReturn(t: T): OngoingStubbing<T> = thenReturn(t)
fun <T> OngoingStubbing<T>.doReturn(t: T, vararg ts: T): OngoingStubbing<T> = thenReturn(t, *ts)
inline infix fun <reified T> OngoingStubbing<T>.doReturn(ts: List<T>): OngoingStubbing<T> = thenReturn(ts[0], *ts.drop(1).toTypedArray())

infix fun <T> OngoingStubbing<T>.doThrow(t: Throwable): OngoingStubbing<T> = thenThrow(t)
fun <T> OngoingStubbing<T>.doThrow(t: Throwable, vararg ts: Throwable): OngoingStubbing<T> = thenThrow(t, *ts)
infix fun <T> OngoingStubbing<T>.doThrow(t: KClass<out Throwable>): OngoingStubbing<T> = thenThrow(t.java)
fun <T> OngoingStubbing<T>.doThrow(t: KClass<out Throwable>, vararg ts: KClass<out Throwable>): OngoingStubbing<T> = thenThrow(t.java, *ts.map { it.java }.toTypedArray())

infix fun <T> OngoingStubbing<T>.doAnswer(answer: (InvocationOnMock) -> T?): OngoingStubbing<T> = thenAnswer(answer)

@Deprecated("V2 Migration", replaceWith = ReplaceWith("mockingDetails", "com.nhaarman.mockitokotlin2.mockingDetails"))
fun mockingDetails(toInspect: Any): MockingDetails = Mockito.mockingDetails(toInspect)!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("never", "com.nhaarman.mockitokotlin2.never"))
fun never(): VerificationMode = Mockito.never()!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("notNull", "com.nhaarman.mockitokotlin2.notNull"))
fun <T : Any> notNull(): T? = Mockito.notNull()
@Deprecated("V2 Migration", replaceWith = ReplaceWith("only", "com.nhaarman.mockitokotlin2.only"))
fun only(): VerificationMode = Mockito.only()!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("refEq", "com.nhaarman.mockitokotlin2.refEq"))
fun <T> refEq(value: T, vararg excludeFields: String): T? = Mockito.refEq(value, *excludeFields)

@Deprecated("V2 Migration", replaceWith = ReplaceWith("reset", "com.nhaarman.mockitokotlin2.reset"))
fun <T> reset(vararg mocks: T) = Mockito.reset(*mocks)

@Deprecated("V2 Migration", replaceWith = ReplaceWith("same", "com.nhaarman.mockitokotlin2.same"))
fun <T> same(value: T): T = Mockito.same(value) ?: value

@Deprecated("V2 Migration", replaceWith = ReplaceWith("spy", "com.nhaarman.mockitokotlin2.spy"))
inline fun <reified T : Any> spy(): T = Mockito.spy(T::class.java)!!
fun <T> spy(value: T): T = Mockito.spy(value)!!

@Deprecated("V2 Migration", replaceWith = ReplaceWith("timeout", "com.nhaarman.mockitokotlin2.timeout"))
fun timeout(millis: Long): VerificationWithTimeout = Mockito.timeout(millis)!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("times", "com.nhaarman.mockitokotlin2.times"))
fun times(numInvocations: Int): VerificationMode = Mockito.times(numInvocations)!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("validateMockitoUsage", "com.nhaarman.mockitokotlin2.validateMockitoUsage"))
fun validateMockitoUsage() = Mockito.validateMockitoUsage()

@Deprecated("V2 Migration", replaceWith = ReplaceWith("verify", "com.nhaarman.mockitokotlin2.verify"))
fun <T> verify(mock: T): T = Mockito.verify(mock)!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("verify", "com.nhaarman.mockitokotlin2.verify"))
fun <T> verify(mock: T, mode: VerificationMode): T = Mockito.verify(mock, mode)!!
@Deprecated("V2 Migration", replaceWith = ReplaceWith("verifyNoMoreInteractions", "com.nhaarman.mockitokotlin2.verifyNoMoreInteractions"))
fun <T> verifyNoMoreInteractions(vararg mocks: T) = Mockito.verifyNoMoreInteractions(*mocks)
@Deprecated("V2 Migration", replaceWith = ReplaceWith("verifyZeroInteractions", "com.nhaarman.mockitokotlin2.verifyZeroInteractions"))
fun verifyZeroInteractions(vararg mocks: Any) = Mockito.verifyZeroInteractions(*mocks)

@Deprecated("V2 Migration", replaceWith = ReplaceWith("whenever", "com.nhaarman.mockitokotlin2.whenever"))
fun <T> whenever(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)!!

@Deprecated("V2 Migration", replaceWith = ReplaceWith("withSettings", "com.nhaarman.mockitokotlin2.withSettings"))
fun withSettings(
      extraInterfaces: Array<KClass<out Any>>? = null,
      name: String? = null,
      spiedInstance: Any? = null,
      defaultAnswer: Answer<Any>? = null,
      serializable: Boolean = false,
      serializableMode: SerializableMode? = null,
      verboseLogging: Boolean = false,
      invocationListeners: Array<InvocationListener>? = null,
      stubOnly: Boolean = false,
      @Incubating useConstructor: Boolean = false,
      @Incubating outerInstance: Any? = null
): MockSettings = Mockito.withSettings().apply {
    extraInterfaces?.let { extraInterfaces(*it.map { it.java }.toTypedArray()) }
    name?.let { name(it) }
    spiedInstance?.let { spiedInstance(it) }
    defaultAnswer?.let { defaultAnswer(it) }
    if (serializable) serializable()
    serializableMode?.let { serializable(it) }
    if (verboseLogging) verboseLogging()
    invocationListeners?.let { invocationListeners(*it) }
    if (stubOnly) stubOnly()
    if (useConstructor) useConstructor()
    outerInstance?.let { outerInstance(it) }
}
