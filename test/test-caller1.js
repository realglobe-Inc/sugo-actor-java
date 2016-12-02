'use strict'

const sugoCaller = require('sugo-caller')
const co = require('co')
const assert = require('assert')
const asleep = require('asleep')

const PORT = process.env.PORT || 8080

const HUB = 'http://localhost:' + PORT
const ACTOR = 'actor0'

co(function * () {
  const caller = sugoCaller(HUB + '/callers')
  try {
    const actor = yield caller.connect(ACTOR)
    const module = actor.get('module')

    assert.equal(yield module.echoBool(true), true)
    assert.equal(yield module.echoNumber(12345), 12345)
    assert.equal(yield module.echoNumber(123.45), 123.45)
    assert.equal(yield module.echoString('abcde'), 'abcde')
    assert.deepEqual(yield module.echoArray([null, false, 0, '']), [null, false, 0, ''])
    // object の null プロパティは今のとこ非対応
    // assert.deepEqual(yield module.echoObject({a: null, b: false, c: 0, d: ''}), {a: null, b: false, c: 0, d: ''})
    assert.deepEqual(yield module.echoObject({b: false, c: 0, d: ''}), {b: false, c: 0, d: ''})

    let b
    let n
    let s
    let a
    let o

    module.on('bool', data => { b = data })
    module.on('number', data => { n = data })
    module.on('string', data => { s = data })
    module.on('array', data => { a = data })
    module.on('object', data => { o = data })

    yield asleep(2000)

    assert.equal(b, true)
    assert.equal(n, 123.45)
    assert.equal(s, 'abcde')
    assert.deepEqual(a, [null, false, 0, ''])
    assert.deepEqual(o, {b: false, c: 0, d: ''})
  } finally {
    caller.disconnect()
  }
}).catch((err) => {
  console.error(err)
  process.exit(1)
})
