/**
 * 
 */
package name.zhangmin.gw.io.rest.test

/**
 * @author ZhangMin.name
 *
 */
class GroovyPlay {

	static main(args) {
		def map = [:]
		map."hello lol" = "allowed"
		assert map."hello lol" == "allowed"
		
		def firstname = "Min"
		map."name-${firstname}" = "Zhang Min"
		println map."name-Min"
		
		
		//Strings
		assert 'ab' == 'a' + 'b' // ordinary java.lang.String
		
		def multiline = '''line one
		line two
		line three'''.stripIndent()
		
		println multiline
		
		//List
		def nums = [1, 2, 3]
		assert nums instanceof List
		assert nums.size() == 3
		nums << 4
		println nums[3]
		
		
		
	}

}
