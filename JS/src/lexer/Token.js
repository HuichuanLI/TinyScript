const TokenType = require("./TokenType");
const AlphabetHelper = require("./AlphabetHelper");
const LexicalException = require("./LexicalException");

const Keywords = new Set([
  "var",
  "if",
  "else",
  "for",
  "while",
  "break",
  "func",
  "return",
  "int",
  "float",
  "bool",
  "void",
  "string"
]);

class Token {
  constructor(type, value) {
    this._type = type;
    this._value = value;
  }

  getType() {
    return this._type;
  }

  getValue() {
    return this._value;
  }

  isVariable() {
    return this._type == TokenType.VARIABLE;
  }

  isValue() {
    return this.isScalar() || this.isVariable();
  }

  isType() {
    return (
      this._value === "bool" ||
      this._value === "int" ||
      this._value === "float" ||
      this._value === "void" ||
      this._value === "string"
    );
  }

  isScalar() {
    return (
      this._type == TokenType.INTEGER ||
      this._type == TokenType.FLOAT ||
      this._type == TokenType.STRING ||
      this._type == TokenType.BOOLEAN
    );
  }

  toString() {
    return `type ${this._type.type}, value ${this._value}`;
  }


  static MakeVarOrKeyword(it) {
    let s = "";

    while (it.hasNext()) {
      const c = it.peek()
      if (AlphabetHelper.isLiteral(c)) {
        s += c;
      } else {
        break;
      }
      // 不变式
      it.next();
      
    }
    if (Keywords.has(s)) {
      return new Token(TokenType.KEYWORD, s);
    }

    if(s == 'true' || s=='false'){
      return new Token(TokenType.BOOLEAN, s);
    }

    return new Token(TokenType.VARIABLE, s);

  }
}