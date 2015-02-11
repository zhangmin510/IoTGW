/**
 * 
 */
package name.zhangmin.gw.io.rest.test
import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.*

println "hello script"

println "Operator"

assert ['cat', 'elephant']*.size() == [3, 8]

class Person { String name}
class Twin {
	Person one, two
	def iterator() {
		return [one, two].iterator()
	}
}

def tw = new Twin(one : new Person(name:'Tom'),
	two : new Person(name:'Tim'))

assert tw*.name == ['Tom', 'Tim']
assert tw.collect{ it.name } == ['Tom', 'Tim']

println tw*.name

def p = new Person()
println p.name?.size()

a = 'b'
println a

println "Script function"

def foo(list, value) {
	println "calling function foo() with param ${value}"
	list << value
}

x = []
foo(x, 1)
foo(x, 2)
assert x == [1, 2]
println "creating list ${x}"

println "closure========="

def clos = { println "hello!"}
clos()

printsum = {a, b -> println a+b}

printsum(5, 4)

def localMethod() {
	def localVariable = new java.util.Date()
	return { println localVariable }
  }
   
  def clos1 = localMethod()
   
  println "Executing the Closure:"
  clos1()
  def list = ['a','b','c','d']
  def newList = []
   
  list.collect( newList ) {
	it.toUpperCase()
  }
  println newList
 
  
  println "File============="
  
  new File("foo.txt").eachLine {line -> println(line)}
  
  def count = 0, maxsize = 100
  new File("foo.txt").withReader { reader ->
	  while (reader.readLine() != null) {
		  if (++count > maxsize) throw new RuntimeException('File too large')
	  }
  }
  
  
  def fields = [ "a":"1", "b":"2"]
  new File("foo.ini").withWriter {out ->
	  fields.each() { key, value ->
		  out.writeLine("${key}=${value}")
	  }
  }
  
  
  println "Stream======"
  
  def fos = new FileOutputStream("testFile.txt")
  
  [21, 23, 44].each{ fos.write(it)}
  
  fos.flush()
  fos.write([32, 22, 34] as byte[])
  fos.write([22,3,12,4] as byte[], 1, 2)
  fos.close()
  try {
	  fos.write(22);assert 0 
  } catch (e) { assert e instanceof IOException}
  
  def fis = new FileInputStream("testFile.txt")
  
  assert fis.available() == 8
  
  new File('testFile.txt').delete()
  
  println "XML======="
  def CAR_RECORDS = '''
    <records>
      <car name='HSV Maloo' make='Holden' year='2006'>
        <country>Australia</country>
        <record type='speed'>Production Pickup Truck with speed of 271kph</record>
      </car>
      <car name='P50' make='Peel' year='1962'>
        <country>Isle of Man</country>
        <record type='size'>Smallest Street-Legal Car at 99cm wide and 59 kg in weight</record>
      </car>
      <car name='Royale' make='Bugatti' year='1931'>
        <country>France</country>
        <record type='price'>Most Valuable Car at $15 million</record>
      </car>
    </records>
  '''
  
  println CAR_RECORDS
  
  def records = new XmlParser().parseText(CAR_RECORDS)
  def all = records.car.size()
  assert all == 3
  println records.car[0]
  
  def first = records.car[0]
  println first.name()
  println first.'@make'
  println first.country.text()
  
  
  
  def writer = new StringWriter()
  def xml = new MarkupBuilder(writer)
  xml.records() {
	car(name:'HSV Maloo', make:'Holden', year:2006) {
	  country('Australia')
	  record(type:'speed', 'Production Pickup Truck with speed of 271kph')
	}
	car(name:'P50', make:'Peel', year:1962) {
	  country('Isle of Man')
	  record(type:'size', 'Smallest Street-Legal Car at 99cm wide and 59 kg in weight')
	}
	car(name:'Royale', make:'Bugatti', year:1931) {
	  country('France')
	  record(type:'price', 'Most Valuable Car at $15 million')
	}
  }
   
  //XMLUnit.setIgnoreWhitespace(true)
 
  println writer.toString()
  
  
  println "Closure"
  
  def benchmark(repeat, Closure worker) {
	  start = System.currentTimeMillis()
	  repeat.times{worker(it)}
	  stop = System.currentTimeMillis()
	  return stop - start
  }
  slow = benchmark(10000) {(int) it / 2}
  fast = benchmark(10000) {it.intdiv(2)}
  
  assert fast * 10 < slow
  println "fast:$fast, slow:$slow"
  
  def adder = { x, y = 5 -> return x + y}
  assert adder(4, 3) == 7
  assert adder.call(7) == 12
  
  
  def addOne = adder.curry(1)
  assert addOne(5) == 6
  
 
 // A log program
  
 def configurator = { format, filter , line ->
	 filter(line) ? format(line) : null
 }
 def appender = { config, append, line ->
	 def out = config(line)
	 if (out) append(out)
 }
 
 def dateFormatter = { line -> "${new Date()} : $line" }
 def debugFilter = {line -> line.contains('debug') }
 def consoleAppender = { line -> println line}
 
 def myConf = configurator.curry(dateFormatter, debugFilter)
 def myLog = appender.curry(myConf, consoleAppender)
 
 myLog("here is some debug message")
 myLog("this will not be printed")
 
 
 assert [1,2,3].grep{ it < 3} == [1,2]
 switch(10) {
	 case {it %2 == 1} : assert false
 }
 
  
  
  