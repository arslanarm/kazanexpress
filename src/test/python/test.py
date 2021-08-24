import flask

app = flask.Flask(__name__)

@app.route("/api/v1/users/<id>", methods=["GET"])
def user(id):
	return flask.jsonify({"id": id, "name": "Test"})

@app.route("/api/v1/orders/", methods=["GET"])
def orders():
	print(flask.request.args.get("client_id"))
	return flask.jsonify([{"id": 1, "productId": 1}])

@app.route("/api/v1/products/<id>", methods=["GET"])
def product(id):
	return flask.jsonify({"id": id, "title": "test"})

app.run(host="0.0.0.0", port=5000)
