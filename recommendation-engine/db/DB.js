const { Datastore } = require("@google-cloud/datastore");

// Creates a client
let datastore = null;

const getConnection = () => {
  if (datastore) {
    return datastore;
  }
  return new Datastore({
    keyFilename:
      process.env.NODE_ENV !== "production"
        ? path.join(__dirname, "account.json")
        : "/etc/google-auth/strikers-chatboot-datastorege-account.json"
  });
};

module.exports = {
  getConnection
};
