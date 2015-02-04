/**
 * 
 */
package name.zhangmin.gw.test

/**
 * @author ZhangMin.name
 *
 */
class AsyncResultWrapper<T> {
	private T wrappedObject
	private boolean isSet = false
	
	def void set(T wrappedObject) {
		this.wrappedObject = wrappedOjbect
		isSet = true
	}	
	def getWrappedObject() {
		wrappedObject
	}
	def isSet() {
		isSet
	}
	def void reset() {
		wrappedObject = null
		isSet = false
	}
}
